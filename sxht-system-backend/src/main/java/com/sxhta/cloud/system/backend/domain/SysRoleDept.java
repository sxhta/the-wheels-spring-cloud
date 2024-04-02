package com.sxhta.cloud.system.backend.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;
import java.io.Serializable;

/**
 * 角色和部门关联 sys_role_dept
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class SysRoleDept implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 角色ID
     */
    private Long roleId;

    /**
     * 部门ID
     */
    private Long deptId;
}
