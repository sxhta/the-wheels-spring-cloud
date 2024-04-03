package com.sxhta.cloud.security.service.impl;


import cn.hutool.core.util.ObjectUtil;
import com.sxhta.cloud.cache.redis.service.RedisService;
import com.sxhta.cloud.common.component.IpComponent;
import com.sxhta.cloud.common.component.JwtComponent;
import com.sxhta.cloud.common.component.ServletComponent;
import com.sxhta.cloud.common.constant.CacheConstants;
import com.sxhta.cloud.common.constant.SecurityConstants;
import com.sxhta.cloud.common.constant.TokenConstants;
import com.sxhta.cloud.common.context.SecurityContextHolder;
import com.sxhta.cloud.common.exception.auth.NotLoginException;
import com.sxhta.cloud.common.utils.uuid.IdUtils;
import com.sxhta.cloud.common.domain.AbstractUserCacheVo;
import com.sxhta.cloud.common.domain.AbstractUserEntity;
import com.sxhta.cloud.security.response.TokenResponse;
import com.sxhta.cloud.security.service.TokenService;
import jakarta.inject.Inject;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.Serial;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * token验证处理
 */
@Service
public class TokenServiceImpl<Cache extends AbstractUserCacheVo<Entity>, Entity
        extends AbstractUserEntity>
        implements TokenService<Cache, Entity>, Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private static final Logger log = LoggerFactory.getLogger(TokenServiceImpl.class);

    @Inject
    private RedisService<String, Cache> redisService;

    protected static final Long MILLIS_SECOND = 1000L;

    protected static final Long MILLIS_MINUTE = 60 * MILLIS_SECOND;

    private final static Long expireTime = CacheConstants.EXPIRATION;

    private final static String ACCESS_TOKEN = CacheConstants.LOGIN_TOKEN_KEY;

    private final static Long MILLIS_MINUTE_TEN = CacheConstants.REFRESH_TIME * MILLIS_MINUTE;

    @Inject
    private ServletComponent servletComponent;

    @Inject
    private SecurityContextHolder securityContextHolder;

    @Inject
    private IpComponent ipComponent;

    @Inject
    private JwtComponent jwtComponent;


    /**
     * 创建令牌
     */
    @Override
    public TokenResponse createToken(Cache userCacheVo) {
        final var token = IdUtils.fastUUID();
        final var userId = userCacheVo.getUserEntity().getUserId();
        final var userName = userCacheVo.getUserEntity().getUserName();
        userCacheVo.setToken(token);
        userCacheVo.setUserid(userId);
        userCacheVo.setUsername(userName);
        userCacheVo.setIpaddr(ipComponent.getIpAddr());
        refreshToken(userCacheVo);

        // Jwt存储信息
        final var claimsMap = new HashMap<String, Object>();
        claimsMap.put(SecurityConstants.USER_KEY, token);
        claimsMap.put(SecurityConstants.DETAILS_USER_ID, userId);
        claimsMap.put(SecurityConstants.DETAILS_USERNAME, userName);

        final var accessToken = jwtComponent.createToken(claimsMap);
        final var response = new TokenResponse();
        response.setAccessToken(accessToken)
                .setExpireTime(expireTime);
        // 接口返回信息
        return response;
    }

    /**
     * 获取用户身份信息
     *
     * @return 用户信息
     */
    @Override
    public Cache getLoginUser() {
        return getLoginUser(servletComponent.getRequest());
    }

    /**
     * 获取用户身份信息
     *
     * @return 用户信息
     */
    @Override
    public Cache getLoginUser(HttpServletRequest request) {
        // 获取请求携带的令牌
        final var token = getToken(request);
        return getLoginUser(token);
    }

    /**
     * 获取用户身份信息
     *
     * @return 用户信息
     */
    @Override
    public Cache getLoginUser(String token) {
        Cache user;
        try {
            if (ObjectUtil.isNotEmpty(token)) {
                final var userkey = jwtComponent.getUserKey(token);
                final var tokenKey = getTokenKey(userkey);
                user = redisService.getCacheObject(tokenKey);
                return user;
            }
        } catch (Exception e) {
            log.error("获取用户信息异常'{}'", e.getMessage());
        }
        return null;
    }


    /**
     * 检验用户是否已经登录，如未登录，则抛出异常
     */
    @Override
    public void checkLogin() {
        final var token = getToken();
        if (ObjectUtil.isNull(token)) {
            throw new NotLoginException("未提供token");
        }
        final var loginUser = getLoginUser();
        if (ObjectUtil.isNull(loginUser)) {
            throw new NotLoginException("无效的token");
        }
    }

    /**
     * 获取用户ID
     */
    @Override
    public Long getUserId() {
        return securityContextHolder.getUserId();
    }

    /**
     * 获取用户名称
     */
    @Override
    public String getUsername() {
        return securityContextHolder.getUserName();
    }

    @Override
    public String getUserKey() {
        return securityContextHolder.getUserKey();
    }

    /**
     * 获取请求token
     */
    @Override
    public String getToken() {
        return getToken(Objects.requireNonNull(servletComponent.getRequest()));
    }

    /**
     * 根据request获取请求token
     */
    @Override
    public String getToken(HttpServletRequest request) {
        // 从header获取token标识
        final var token = request.getHeader(TokenConstants.AUTHENTICATION);
        return replaceTokenPrefix(token);
    }

    @Override
    public String replaceTokenPrefix(String token) {
        // 如果前端设置了令牌前缀，则裁剪掉前缀
        if (ObjectUtil.isNotEmpty(token) && token.startsWith(TokenConstants.PREFIX)) {
            token = token.replaceFirst(TokenConstants.PREFIX, "");
        }
        return token;
    }

    /**
     * 设置用户身份信息
     */
    @Override
    public void setLoginUser(Cache userCacheVo) {
        if (ObjectUtil.isNotNull(userCacheVo) && ObjectUtil.isNotEmpty(userCacheVo.getToken())) {
            refreshToken(userCacheVo);
        }
    }

    /**
     * 删除用户缓存信息
     */
    @Override
    public Boolean deleteLoginUser(String token) {
        if (ObjectUtil.isNotEmpty(token)) {
            final var userKey = jwtComponent.getUserKey(token);
            return redisService.deleteObject(getTokenKey(userKey));
        }
        return false;
    }

    /**
     * 验证令牌有效期，相差不足120分钟，自动刷新缓存
     */
    @Override
    public void verifyAndRefreshToken(Cache userCacheVo) {
        final var expireTime = userCacheVo.getExpireTime();
        final var currentTime = System.currentTimeMillis();
        if (expireTime - currentTime <= MILLIS_MINUTE_TEN) {
            refreshToken(userCacheVo);
        }
    }

    /**
     * 刷新令牌有效期
     *
     * @param userCacheVo 登录信息
     */
    @Override
    public void refreshToken(Cache userCacheVo) {
        userCacheVo.setLoginTime(System.currentTimeMillis());
        userCacheVo.setExpireTime(userCacheVo.getLoginTime() + expireTime * MILLIS_MINUTE);
        // 根据uuid将loginUser缓存
        final var userKey = getTokenKey(userCacheVo.getToken());
        redisService.setCacheObject(userKey, userCacheVo, expireTime, TimeUnit.MINUTES);
    }

    @Override
    public String getTokenKey(String token) {
        return ACCESS_TOKEN + token;
    }
}
