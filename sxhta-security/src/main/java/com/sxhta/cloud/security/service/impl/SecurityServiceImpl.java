package com.sxhta.cloud.security.service.impl;

import com.sxhta.cloud.security.service.SecurityService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.Serial;
import java.io.Serializable;

/**
 * 权限获取工具类
 */
@Service
public class SecurityServiceImpl implements SecurityService, Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 生成BCryptPasswordEncoder密码
     *
     * @param password 密码
     * @return 加密字符串
     */
    @Override
    public String encryptPassword(String password) {
        final var passwordEncoder = new BCryptPasswordEncoder();
        return passwordEncoder.encode(password);
    }

    /**
     * 判断密码是否相同
     *
     * @param rawPassword     真实密码
     * @param encodedPassword 加密后字符
     * @return 结果
     */
    @Override
    public Boolean matchesPassword(String rawPassword, String encodedPassword) {
        final var passwordEncoder = new BCryptPasswordEncoder();
        return passwordEncoder.matches(rawPassword, encodedPassword);
    }
}
