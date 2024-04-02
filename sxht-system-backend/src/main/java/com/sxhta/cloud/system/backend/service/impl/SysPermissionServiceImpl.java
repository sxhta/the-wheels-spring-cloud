package com.sxhta.cloud.system.backend.service.impl;

import com.sxhta.cloud.remote.domain.SysUser;
import com.sxhta.cloud.remote.util.AdminChecker;
import com.sxhta.cloud.system.backend.service.ISysMenuService;
import com.sxhta.cloud.system.backend.service.ISysPermissionService;
import com.sxhta.cloud.system.backend.service.ISysRoleService;
import jakarta.inject.Inject;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.HashSet;
import java.util.Set;

/**
 * 用户权限处理
 */
@Service
public class SysPermissionServiceImpl implements ISysPermissionService {

    @Inject
    private ISysRoleService roleService;

    @Inject
    private ISysMenuService menuService;

    /**
     * 获取角色数据权限
     *
     * @return 角色权限信息
     */
    @Override
    public Set<String> getRolePermission(SysUser user) {
        final var roles = new HashSet<String>();
        // 管理员拥有所有权限
        if (AdminChecker.isAdminUser(user.getUserId())) {
            roles.add("admin");
        } else {
            final var userId = user.getUserId();
            roles.addAll(roleService.selectRolePermissionByUserId(userId));
        }
        return roles;
    }

    /**
     * 获取菜单数据权限
     *
     * @return 菜单权限信息
     */
    @Override
    public Set<String> getMenuPermission(SysUser user) {
        final var perms = new HashSet<String>();
        // 管理员拥有所有权限
        if (AdminChecker.isAdminUser(user.getUserId())) {
            perms.add("*:*:*");
        } else {
            final var roles = user.getRoles();
            if (!CollectionUtils.isEmpty(roles)) {
                // 多角色设置permissions属性，以便数据权限匹配权限
                for (final var role : roles) {
                    final var rolePerms = menuService.selectMenuPermsByRoleId(role.getRoleId());
                    role.setPermissions(rolePerms);
                    perms.addAll(rolePerms);
                }
            } else {
                perms.addAll(menuService.selectMenuPermsByUserId(user.getUserId()));
            }
        }
        return perms;
    }
}
