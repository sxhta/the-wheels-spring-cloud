package com.sxhta.cloud.system.backend.response;

import com.sxhta.cloud.remote.domain.SysUser;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serial;
import java.io.Serializable;

@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
public final class UserProfileResponse implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private SysUser user;

    private String roleGroup;

    private String postGroup;
}
