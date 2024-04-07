package com.sxhta.cloud.system.backend.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;
import java.io.Serializable;

/**
 * 当前在线会话
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class SysUserOnline implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 会话编号
     */
    private String tokenId;

    /**
     * 用户名称
     */
    private String userName;

    /**
     * 登录IP地址
     */
    private String ipaddr;

    /**
     * 登录地址
     */
    private String loginLocation;

    /**
     * 浏览器类型
     */
    private String browser;

    /**
     * 操作系统
     */
    private String os;

    /**
     * 登录时间
     */
    private Long loginTime;

}
