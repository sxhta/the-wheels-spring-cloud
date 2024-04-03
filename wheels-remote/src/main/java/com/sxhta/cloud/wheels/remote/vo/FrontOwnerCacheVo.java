package com.sxhta.cloud.wheels.remote.vo;


import com.sxhta.cloud.common.domain.AbstractUserCacheVo;
import com.sxhta.cloud.wheels.remote.domain.owner.WheelsFrontOwner;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;
import java.io.Serializable;
import java.util.Set;

/**
 * 用户信息
 * 存储在Redis中的用户信息
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class FrontOwnerCacheVo extends AbstractUserCacheVo<WheelsFrontOwner> implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 权限列表
     */
    private Set<String> permissions;

    /**
     * 角色列表
     */
    private Set<String> roles;
}
