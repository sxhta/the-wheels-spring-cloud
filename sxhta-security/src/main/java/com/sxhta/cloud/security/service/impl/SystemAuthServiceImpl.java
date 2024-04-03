package com.sxhta.cloud.security.service.impl;


import com.sxhta.cloud.common.context.SecurityContextHolder;
import com.sxhta.cloud.common.exception.auth.NotPermissionException;
import com.sxhta.cloud.common.exception.auth.NotRoleException;
import com.sxhta.cloud.common.utils.CommonStringUtil;
import com.sxhta.cloud.remote.domain.SysUser;
import com.sxhta.cloud.remote.vo.SystemUserCacheVo;
import com.sxhta.cloud.security.annotation.Logical;
import com.sxhta.cloud.security.annotation.RequiresLogin;
import com.sxhta.cloud.security.annotation.RequiresPermissions;
import com.sxhta.cloud.security.annotation.RequiresRoles;
import com.sxhta.cloud.security.service.SystemAuthService;
import com.sxhta.cloud.security.service.TokenService;
import jakarta.inject.Inject;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.PatternMatchUtils;

import java.io.Serial;
import java.io.Serializable;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * Token 权限验证，逻辑实现类
 */
@Service
public class SystemAuthServiceImpl implements SystemAuthService, Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 所有权限标识
     */
    private static final String ALL_PERMISSION = "*:*:*";

    /**
     * 管理员角色权限标识
     */
    private static final String SUPER_ADMIN = "admin";

    @Inject
    private TokenService<SystemUserCacheVo, SysUser> tokenService;


    @Inject
    private SecurityContextHolder securityContextHolder;

    /**
     * 验证用户是否具备某权限
     *
     * @param permission 权限字符串
     * @return 用户是否具备某权限
     */
    @Override
    public Boolean hasPermission(String permission) {
        return hasPermission(getPermissionList(), permission);
    }

    /**
     * 验证用户是否具备某权限, 如果验证未通过，则抛出异常: NotPermissionException
     */
    @Override
    public void checkPermission(String permission) {
        if (!hasPermission(getPermissionList(), permission)) {
            throw new NotPermissionException(permission);
        }
    }

    /**
     * 根据注解(@RequiresPermissions)鉴权, 如果验证未通过，则抛出异常: NotPermissionException
     *
     * @param requiresPermissions 注解对象
     */
    @Override
    public void checkPermission(RequiresPermissions requiresPermissions) {
        securityContextHolder.setPermission(StringUtils.join(requiresPermissions.value(), ","));
        if (requiresPermissions.logical() == Logical.AND) {
            checkPermissionAnd(requiresPermissions.value());
        } else {
            checkPermissionOr(requiresPermissions.value());
        }
    }

    /**
     * 验证用户是否含有指定权限，必须全部拥有
     *
     * @param permissions 权限列表
     */
    @Override
    public void checkPermissionAnd(String... permissions) {
        final var permissionList = getPermissionList();
        for (final var permission : permissions) {
            if (!hasPermission(permissionList, permission)) {
                throw new NotPermissionException(permission);
            }
        }
    }

    /**
     * 验证用户是否含有指定权限，只需包含其中一个
     *
     * @param permissions 权限码数组
     */
    @Override
    public void checkPermissionOr(String... permissions) {
        final var permissionList = getPermissionList();
        for (final var permission : permissions) {
            if (hasPermission(permissionList, permission)) {
                return;
            }
        }
        if (permissions.length > 0) {
            throw new NotPermissionException(permissions);
        }
    }

    /**
     * 判断用户是否拥有某个角色
     *
     * @param role 角色标识
     * @return 用户是否具备某角色
     */
    @Override
    public Boolean hasRole(String role) {
        return hasRole(getRoleList(), role);
    }

    /**
     * 判断用户是否拥有某个角色, 如果验证未通过，则抛出异常: NotRoleException
     *
     * @param role 角色标识
     */
    @Override
    public void checkRole(String role) {
        if (!hasRole(role)) {
            throw new NotRoleException(role);
        }
    }

    /**
     * 根据注解(@RequiresRoles)鉴权
     *
     * @param requiresRoles 注解对象
     */
    @Override
    public void checkRole(RequiresRoles requiresRoles) {
        if (requiresRoles.logical() == Logical.AND) {
            checkRoleAnd(requiresRoles.value());
        } else {
            checkRoleOr(requiresRoles.value());
        }
    }

    /**
     * 验证用户是否含有指定角色，必须全部拥有
     *
     * @param roles 角色标识数组
     */
    @Override
    public void checkRoleAnd(String... roles) {
        final var roleList = getRoleList();
        for (final var role : roles) {
            if (!hasRole(roleList, role)) {
                throw new NotRoleException(role);
            }
        }
    }

    /**
     * 验证用户是否含有指定角色，只需包含其中一个
     *
     * @param roles 角色标识数组
     */
    @Override
    public void checkRoleOr(String... roles) {
        final var roleList = getRoleList();
        for (final var role : roles) {
            if (hasRole(roleList, role)) {
                return;
            }
        }
        if (roles.length > 0) {
            throw new NotRoleException(roles);
        }
    }

    /**
     * 根据注解(@RequiresLogin)鉴权
     *
     * @param at 注解对象
     */
    @Override
    public void checkByAnnotation(RequiresLogin at) {
        tokenService.checkLogin();
    }

    /**
     * 根据注解(@RequiresRoles)鉴权
     *
     * @param at 注解对象
     */
    @Override
    public void checkByAnnotation(RequiresRoles at) {
        final var roleArray = at.value();
        if (at.logical() == Logical.AND) {
            this.checkRoleAnd(roleArray);
        } else {
            this.checkRoleOr(roleArray);
        }
    }

    /**
     * 根据注解(@RequiresPermissions)鉴权
     *
     * @param at 注解对象
     */
    @Override
    public void checkByAnnotation(RequiresPermissions at) {
        final var permissionArray = at.value();
        if (at.logical() == Logical.AND) {
            this.checkPermissionAnd(permissionArray);
        } else {
            this.checkPermissionOr(permissionArray);
        }
    }

    /**
     * 获取当前账号的角色列表
     *
     * @return 角色列表
     */
    @Override
    public Set<String> getRoleList() {
        try {
            final var loginUser = tokenService.getLoginUser();
            return loginUser.getRoles();
        } catch (Exception e) {
            return new HashSet<>();
        }
    }

    /**
     * 获取当前账号的权限列表
     *
     * @return 权限列表
     */
    @Override
    public Set<String> getPermissionList() {
        try {
            final var loginUser = tokenService.getLoginUser();
            return loginUser.getPermissions();
        } catch (Exception e) {
            return new HashSet<>();
        }
    }

    /**
     * 判断是否包含权限
     *
     * @param authorities 权限列表
     * @param permission  权限字符串
     * @return 用户是否具备某权限
     */
    @Override
    public Boolean hasPermission(Collection<String> authorities, String permission) {
        return authorities.stream().filter(CommonStringUtil::hasText)
                .anyMatch(x -> ALL_PERMISSION.contains(x) || PatternMatchUtils.simpleMatch(x, permission));
    }

    /**
     * 判断是否包含角色
     *
     * @param roles 角色列表
     * @param role  角色
     * @return 用户是否具备某角色权限
     */
    @Override
    public Boolean hasRole(Collection<String> roles, String role) {
        return roles.stream().filter(CommonStringUtil::hasText)
                .anyMatch(x -> SUPER_ADMIN.contains(x) || PatternMatchUtils.simpleMatch(x, role));
    }
}
