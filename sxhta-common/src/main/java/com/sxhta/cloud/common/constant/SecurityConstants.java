package com.sxhta.cloud.common.constant;

import java.io.Serial;
import java.io.Serializable;

/**
 * 权限相关通用常量
 */
public class SecurityConstants implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 用户ID字段
     */
    public static final String DETAILS_USER_ID = "user_id";
    /**
     * 用户hash字段
     */
    public static final String DETAILS_USER_HASH = "hash";

    /**
     * 用户名字段
     */
    public static final String DETAILS_USERNAME = "username";

    /**
     * 授权信息字段
     */
    public static final String AUTHORIZATION_HEADER = "authorization";

    /**
     * 请求来源
     */
    public static final String FROM_SOURCE = "from-source";

    /**
     * 内部请求
     */
    public static final String INNER = "inner";

    /**
     * 用户标识
     */
    public static final String USER_KEY = "user_key";

    /**
     * 登录用户
     */
    public static final String LOGIN_USER = "login_user";

    /**
     * 角色权限
     */
    public static final String ROLE_PERMISSION = "role_permission";
}
