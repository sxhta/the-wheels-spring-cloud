package com.sxhta.cloud.security.service;

public interface SecurityService {

    /**
     * 生成BCryptPasswordEncoder密码
     *
     * @param password 密码
     * @return 加密字符串
     */
    String encryptPassword(String password);

    /**
     * 判断密码是否相同
     *
     * @param rawPassword     真实密码
     * @param encodedPassword 加密后字符
     * @return 结果
     */
    Boolean matchesPassword(String rawPassword, String encodedPassword);
}
