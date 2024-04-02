package com.sxhta.cloud.system.backend.response;

import com.sxhta.cloud.remote.domain.SysRole;
import com.sxhta.cloud.remote.domain.SysUser;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
public final class UserRolesResponse implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private SysUser user;

    private List<SysRole> roles;
}
