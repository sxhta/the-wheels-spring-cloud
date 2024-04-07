package com.sxhta.cloud.common.domain;


import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;
import java.io.Serializable;

/**
 * 用户信息
 * 存储在Redis中的用户信息
 */
@Data
@EqualsAndHashCode(callSuper = false)
public abstract class AbstractUserCacheVo<Entity extends AbstractUserEntity> implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 用户唯一标识
     */
    private String token;

    /**
     * 用户名id
     */
    private Long userid;

    /**
     * 用户名
     */
    private String username;

    /**
     * 登录时间
     */
    private Long loginTime;

    /**
     * 过期时间
     */
    private Long expireTime;

    /**
     * 登录IP地址
     */
    private String ipaddr;

    /**
     * 用户信息
     */
    private Entity userEntity;
}
