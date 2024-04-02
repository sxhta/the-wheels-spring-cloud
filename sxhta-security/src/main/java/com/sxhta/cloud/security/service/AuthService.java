package com.sxhta.cloud.security.service;

import com.sxhta.cloud.security.annotation.RequiresLogin;
import com.sxhta.cloud.security.annotation.RequiresPermissions;
import com.sxhta.cloud.security.annotation.RequiresRoles;

import java.util.Collection;
import java.util.Set;

/**
 * 只针对角色权限的逻辑
 */
public interface AuthService {

    /**
     * 验证用户是否具备某权限
     *
     * @param permission 权限字符串
     * @return 用户是否具备某权限
     */
    Boolean hasPermission(String permission);

    /**
     * 验证用户是否具备某权限, 如果验证未通过，则抛出异常: NotPermissionException
     */
    void checkPermission(String permission);

    /**
     * 根据注解(@RequiresPermissions)鉴权, 如果验证未通过，则抛出异常: NotPermissionException
     *
     * @param requiresPermissions 注解对象
     */
    void checkPermission(RequiresPermissions requiresPermissions);

    /**
     * 验证用户是否含有指定权限，必须全部拥有
     *
     * @param permissions 权限列表
     */
    void checkPermissionAnd(String... permissions);

    /**
     * 验证用户是否含有指定权限，只需包含其中一个
     *
     * @param permissions 权限码数组
     */
    void checkPermissionOr(String... permissions);

    /**
     * 判断用户是否拥有某个角色
     *
     * @param role 角色标识
     * @return 用户是否具备某角色
     */
    Boolean hasRole(String role);

    /**
     * 判断用户是否拥有某个角色, 如果验证未通过，则抛出异常: NotRoleException
     *
     * @param role 角色标识
     */
    void checkRole(String role);

    /**
     * 根据注解(@RequiresRoles)鉴权
     *
     * @param requiresRoles 注解对象
     */
    void checkRole(RequiresRoles requiresRoles);

    /**
     * 验证用户是否含有指定角色，必须全部拥有
     *
     * @param roles 角色标识数组
     */
    void checkRoleAnd(String... roles);

    /**
     * 验证用户是否含有指定角色，只需包含其中一个
     *
     * @param roles 角色标识数组
     */
    void checkRoleOr(String... roles);

    /**
     * 根据注解(@RequiresLogin)鉴权
     *
     * @param at 注解对象
     */
    void checkByAnnotation(RequiresLogin at);

    /**
     * 根据注解(@RequiresRoles)鉴权
     *
     * @param at 注解对象
     */
    void checkByAnnotation(RequiresRoles at);

    /**
     * 根据注解(@RequiresPermissions)鉴权
     *
     * @param at 注解对象
     */
    void checkByAnnotation(RequiresPermissions at);

    /**
     * 获取当前账号的角色列表
     *
     * @return 角色列表
     */
    Set<String> getRoleList();

    /**
     * 获取当前账号的权限列表
     *
     * @return 权限列表
     */
    Set<String> getPermissionList();

    /**
     * 判断是否包含权限
     *
     * @param authorities 权限列表
     * @param permission  权限字符串
     * @return 用户是否具备某权限
     */
    Boolean hasPermission(Collection<String> authorities, String permission);

    /**
     * 判断是否包含角色
     *
     * @param roles 角色列表
     * @param role  角色
     * @return 用户是否具备某角色权限
     */
    Boolean hasRole(Collection<String> roles, String role);
}
