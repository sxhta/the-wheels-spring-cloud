package com.sxhta.cloud.common.component;

import io.jsonwebtoken.Claims;

import javax.crypto.SecretKey;
import java.util.Map;

/**
 * Jwt工具类
 */
public interface JwtComponent {

    SecretKey getKey();

    /**
     * 从数据声明生成令牌
     *
     * @param claims 数据声明
     * @return 令牌
     */
    String createToken(Map<String, Object> claims);

    /**
     * 从令牌中获取数据声明
     *
     * @param token 令牌
     * @return 数据声明
     */
    Claims parseToken(String token);

    /**
     * 根据令牌获取用户标识
     *
     * @param token 令牌
     * @return 用户ID
     */
    String getUserKey(String token);

    /**
     * 根据令牌获取用户标识
     *
     * @param claims 身份信息
     * @return 用户ID
     */
    String getUserKey(Claims claims);

    /**
     * 根据令牌获取用户ID
     *
     * @param token 令牌
     * @return 用户ID
     */
    String getUserId(String token);

    /**
     * 根据身份信息获取用户ID
     *
     * @param claims 身份信息
     * @return 用户ID
     */
    String getUserId(Claims claims);

    /**
     * 根据令牌获取用户名
     *
     * @param token 令牌
     * @return 用户名
     */
    String getUserName(String token);

    /**
     * 根据身份信息获取用户名
     *
     * @param claims 身份信息
     * @return 用户名
     */
    String getUserName(Claims claims);

    String getUserHash(Claims claims);

    /**
     * 根据身份信息获取键值
     *
     * @param claims 身份信息
     * @param key    键
     * @return 值
     */
    String getValue(Claims claims, String key);
}
