package com.wheels.cloud.frontend.service.auth.impl;

import cn.hutool.core.util.StrUtil;
import com.sxhta.cloud.cache.redis.service.RedisService;
import com.sxhta.cloud.common.constant.CacheConstants;
import com.sxhta.cloud.common.utils.uuid.IdUtils;
import com.wheels.cloud.frontend.domain.user.FrontUser;
import com.wheels.cloud.frontend.service.auth.FrontTokenService;
import com.wheels.cloud.frontend.vo.auth.UserDetailsVo;
import jakarta.inject.Inject;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class FrontTokenServiceImpl implements FrontTokenService {

    private static final long MILLIS_SECOND = 1000;
    private static final long MILLIS_MINUTE = 60 * MILLIS_SECOND;

    private final static long expireTime = CacheConstants.EXPIRATION;

    private final static String ACCESS_TOKEN = CacheConstants.LOGIN_TOKEN_KEY;

    @Inject
    private RedisService<String, UserDetailsVo> redisService;


    @Override
    public UserDetailsVo createToken(FrontUser frontUser) {
        final var token = IdUtils.randomUUID();
        final var account = frontUser.getAccount();
        final var userId = frontUser.getId();
        final var userDetailsVo = new UserDetailsVo(frontUser);
        userDetailsVo.setUserId(userId)
                .setAccount(account)
                .setToken(token);

        return refreshToken(userDetailsVo);
    }

    @Override
    public UserDetailsVo refreshToken(UserDetailsVo userDetailsVo) {
        userDetailsVo.setLoginTime(System.currentTimeMillis());
        userDetailsVo.setExpireTime(userDetailsVo.getLoginTime() + expireTime * MILLIS_MINUTE);
        // 根据uuid将loginUser缓存
        final var userKey = getTokenKey(userDetailsVo.getToken());
        redisService.setCacheObject(userKey, userDetailsVo, expireTime, TimeUnit.MINUTES);
        return userDetailsVo;
    }

    @Override
    public UserDetailsVo getUserDetailsVoByHttpServletRequest(HttpServletRequest request) {
        // 获取请求携带的令牌
        final var token = getJwtTokenByHttpServletRequest(request);
        if (StrUtil.isNotEmpty(token)) {
            final String userKey = getTokenKey(token);
            final var cache = redisService.getCacheObject(userKey);
            System.out.println(cache);
            return cache;
        }
        return null;
    }

    @Override
    public String getJwtTokenByHttpServletRequest(HttpServletRequest request) {
        var jwtToken = request.getHeader("Authorization");
        if (StrUtil.isNotEmpty(jwtToken)) {
            return jwtToken;
        }
//        throw new CommonNullException("Header中Token为空");
        return null;
    }

    @Override
    public String getTokenKey(String token) {
        return ACCESS_TOKEN + token;
    }

    @Override
    public Boolean deleteToken(String tokenKey) {
        return redisService.deleteObject(tokenKey);
    }
}
