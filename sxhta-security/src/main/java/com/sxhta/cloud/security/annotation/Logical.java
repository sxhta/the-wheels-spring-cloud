package com.sxhta.cloud.security.annotation;

import java.io.Serializable;

/**
 * 权限注解的验证模式
 */
public enum Logical implements Serializable {

    /**
     * 必须具有所有的元素
     */
    AND,

    /**
     * 只需具有其中一个元素
     */
    OR
}
