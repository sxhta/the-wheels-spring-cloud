package com.sxhta.cloud.common.component.impl;

import com.sxhta.cloud.common.component.JwtComponent;
import com.sxhta.cloud.common.constant.SecurityConstants;
import com.sxhta.cloud.common.constant.TokenConstants;
import com.sxhta.cloud.common.text.Convert;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.inject.Singleton;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.io.Serial;
import java.io.Serializable;
import java.util.Map;

/**
 * Jwt工具类
 */
@Singleton
@Component
public class JwtComponentImpl implements JwtComponent, Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private static final String secret = TokenConstants.SECRET;

    @Override
    public SecretKey getKey() {
        final var keyBytes = Decoders.BASE64.decode(secret);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    /**
     * 从数据声明生成令牌
     *
     * @param claims 数据声明
     * @return 令牌
     */
    @Override
    public String createToken(Map<String, Object> claims) {
        final var key = getKey();
        return Jwts.builder().claims(claims).signWith(key).compact();
    }

    /**
     * 从令牌中获取数据声明
     *
     * @param token 令牌
     * @return 数据声明
     */
    @Override
    public Claims parseToken(String token) {
        final var key = getKey();
        return Jwts.parser().verifyWith(key).build().parseSignedClaims(token).getPayload();
    }

    /**
     * 根据令牌获取用户标识
     *
     * @param token 令牌
     * @return 用户ID
     */
    @Override
    public String getUserKey(String token) {
        final var claims = parseToken(token);
        return getValue(claims, SecurityConstants.USER_KEY);
    }

    /**
     * 根据令牌获取用户标识
     *
     * @param claims 身份信息
     * @return 用户ID
     */
    @Override
    public String getUserKey(Claims claims) {
        return getValue(claims, SecurityConstants.USER_KEY);
    }

    /**
     * 根据令牌获取用户ID
     *
     * @param token 令牌
     * @return 用户ID
     */
    @Override
    public String getUserId(String token) {
        final var claims = parseToken(token);
        return getValue(claims, SecurityConstants.DETAILS_USER_ID);
    }

    /**
     * 根据身份信息获取用户ID
     *
     * @param claims 身份信息
     * @return 用户ID
     */
    @Override
    public String getUserId(Claims claims) {
        return getValue(claims, SecurityConstants.DETAILS_USER_ID);
    }

    /**
     * 根据令牌获取用户名
     *
     * @param token 令牌
     * @return 用户名
     */
    @Override
    public String getUserName(String token) {
        final var claims = parseToken(token);
        return getValue(claims, SecurityConstants.DETAILS_USERNAME);
    }

    /**
     * 根据身份信息获取用户名
     *
     * @param claims 身份信息
     * @return 用户名
     */
    @Override
    public String getUserName(Claims claims) {
        return getValue(claims, SecurityConstants.DETAILS_USERNAME);
    }

    /**
     * 根据身份信息获取键值
     *
     * @param claims 身份信息
     * @param key    键
     * @return 值
     */
    @Override
    public String getValue(Claims claims, String key) {
        return Convert.toStr(claims.get(key), "");
    }
}
