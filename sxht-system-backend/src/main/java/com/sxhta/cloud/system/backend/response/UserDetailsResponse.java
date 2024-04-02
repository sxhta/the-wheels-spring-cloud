package com.sxhta.cloud.system.backend.response;

import com.sxhta.cloud.remote.domain.SysRole;
import com.sxhta.cloud.remote.domain.SysUser;
import com.sxhta.cloud.system.backend.domain.SysPost;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;
import java.util.Set;

@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
public final class UserDetailsResponse implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private SysUser user;

    private List<SysPost> posts;

    private List<SysRole> roles;

    private Set<String> permissions;

    private List<Long> postIds;

    private List<Long> roleIds;

}
