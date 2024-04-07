package com.sxhta.cloud.system.backend.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;
import java.io.Serializable;

/**
 * 用户和岗位关联 sys_user_post
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class SysUserPost implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;
    /**
     * 用户ID
     */
    private Long userId;

    /**
     * 岗位ID
     */
    private Long postId;
}
