package com.sxhta.cloud.security.service;

import com.sxhta.cloud.common.domain.AbstractUserCacheVo;
import com.sxhta.cloud.common.domain.AbstractUserEntity;
import com.sxhta.cloud.security.response.TokenResponse;
import jakarta.servlet.http.HttpServletRequest;

public interface TokenService<Cache extends AbstractUserCacheVo<Entity>, Entity extends AbstractUserEntity> {

    /**
     * 创建令牌
     */
    TokenResponse createToken(Cache loginUser);

    /**
     * 获取用户身份信息
     *
     * @return 用户信息
     */
    Cache getLoginUser();

    /**
     * 获取用户身份信息
     *
     * @return 用户信息
     */
    Cache getLoginUser(HttpServletRequest request);

    /**
     * 获取用户身份信息
     *
     * @return 用户信息
     */
    Cache getLoginUser(String token);

    void checkLogin();

    /**
     * 获取用户ID
     */
    Long getUserId();

    /**
     * 获取用户名称
     */
    String getUsername();

    /**
     * 获取用户key
     */
    String getUserKey();

    /**
     * 获取请求token
     */
    String getToken();

    /**
     * 根据request获取请求token
     */
    String getToken(HttpServletRequest request);

    /**
     * 裁剪token前缀
     */
    String replaceTokenPrefix(String token);

    /**
     * 设置用户身份信息
     */
    void setLoginUser(Cache loginUser);

    /**
     * 删除用户缓存信息
     */
    Boolean deleteLoginUser(String token);

    /**
     * 验证令牌有效期，相差不足120分钟，自动刷新缓存
     */
    void verifyAndRefreshToken(Cache loginUser);

    /**
     * 刷新令牌有效期
     *
     * @param loginUser 登录信息
     */
    void refreshToken(Cache loginUser);

    String getTokenKey(String token);
}
