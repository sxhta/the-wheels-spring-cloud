package com.sxhta.cloud.system.backend.service.impl;


import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.extension.toolkit.SqlHelper;
import com.sxhta.cloud.common.constant.UserConstants;
import com.sxhta.cloud.common.exception.ServiceException;
import com.sxhta.cloud.remote.domain.SysRole;
import com.sxhta.cloud.remote.domain.SysUser;
import com.sxhta.cloud.remote.model.SystemUserCacheVo;
import com.sxhta.cloud.remote.util.AdminChecker;
import com.sxhta.cloud.security.datascop.annotation.DataScope;
import com.sxhta.cloud.security.service.TokenService;
import com.sxhta.cloud.system.backend.domain.SysRoleDept;
import com.sxhta.cloud.system.backend.domain.SysRoleMenu;
import com.sxhta.cloud.system.backend.domain.SysUserRole;
import com.sxhta.cloud.system.backend.mapper.SysRoleDeptMapper;
import com.sxhta.cloud.system.backend.mapper.SysRoleMapper;
import com.sxhta.cloud.system.backend.mapper.SysRoleMenuMapper;
import com.sxhta.cloud.system.backend.mapper.SysUserRoleMapper;
import com.sxhta.cloud.system.backend.service.ISysRoleService;
import jakarta.inject.Inject;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * 角色 业务层处理
 */
@Service
public class SysRoleServiceImpl implements ISysRoleService {

    @Inject
    private SysRoleMapper roleMapper;

    @Inject
    private SysRoleMenuMapper roleMenuMapper;

    @Inject
    private SysUserRoleMapper userRoleMapper;

    @Inject
    private SysRoleDeptMapper roleDeptMapper;

    @Inject
    private TokenService<SystemUserCacheVo, SysUser> tokenService;

    /**
     * 根据条件分页查询角色数据
     *
     * @param role 角色信息
     * @return 角色数据集合信息
     */
    @Override
    @DataScope(deptAlias = "d")
    public List<SysRole> selectRoleList(SysRole role) {
        return roleMapper.selectRoleList(role);
    }

    /**
     * 根据用户ID查询角色
     *
     * @param userId 用户ID
     * @return 角色列表
     */
    @Override
    public List<SysRole> selectRolesByUserId(Long userId) {
        final var userRoles = roleMapper.selectRolePermissionByUserId(userId);
        final var roles = selectRoleAll();
        for (final var role : roles) {
            for (final var userRole : userRoles) {
                if (role.getRoleId().longValue() == userRole.getRoleId().longValue()) {
                    role.setFlag(true);
                    break;
                }
            }
        }
        return roles;
    }

    /**
     * 根据用户ID查询权限
     *
     * @param userId 用户ID
     * @return 权限列表
     */
    @Override
    public Set<String> selectRolePermissionByUserId(Long userId) {
        final var perms = roleMapper.selectRolePermissionByUserId(userId);
        final var permsSet = new HashSet<String>();
        for (final var perm : perms) {
            if (ObjectUtil.isNotNull(perm)) {
                permsSet.addAll(Arrays.asList(perm.getRoleKey().trim().split(",")));
            }
        }
        return permsSet;
    }

    /**
     * 查询所有角色
     *
     * @return 角色列表
     */
    @Override
    public List<SysRole> selectRoleAll() {
        return selectRoleList(new SysRole());
    }

    /**
     * 根据用户ID获取角色选择框列表
     *
     * @param userId 用户ID
     * @return 选中角色ID列表
     */
    @Override
    public List<Long> selectRoleListByUserId(Long userId) {
        return roleMapper.selectRoleListByUserId(userId);
    }

    /**
     * 通过角色ID查询角色
     *
     * @param roleId 角色ID
     * @return 角色对象信息
     */
    @Override
    public SysRole selectRoleById(Long roleId) {
        return roleMapper.selectRoleById(roleId);
    }

    /**
     * 校验角色名称是否唯一
     *
     * @param role 角色信息
     * @return 结果
     */
    @Override
    public Boolean checkRoleNameUnique(SysRole role) {
        final var roleId = ObjectUtil.isNull(role.getRoleId()) ? -1L : role.getRoleId();
        final var info = roleMapper.checkRoleNameUnique(role.getRoleName());
        if (ObjUtil.isNotNull(info) && !info.getRoleId().equals(roleId)) {
            return UserConstants.NOT_UNIQUE;
        }
        return UserConstants.UNIQUE;
    }

    /**
     * 校验角色权限是否唯一
     *
     * @param role 角色信息
     * @return 结果
     */
    @Override
    public Boolean checkRoleKeyUnique(SysRole role) {
        final var roleId = ObjectUtil.isNull(role.getRoleId()) ? -1L : role.getRoleId();
        final var info = roleMapper.checkRoleKeyUnique(role.getRoleKey());
        if (ObjectUtil.isNotNull(info) && !info.getRoleId().equals(roleId)) {
            return UserConstants.NOT_UNIQUE;
        }
        return UserConstants.UNIQUE;
    }

    /**
     * 校验角色是否允许操作
     *
     * @param role 角色信息
     */
    @Override
    public void checkRoleAllowed(SysRole role) {
        if (ObjectUtil.isNotNull(role.getRoleId()) && AdminChecker.isAdminRole(role.getRoleId())) {
            throw new ServiceException("不允许操作超级管理员角色");
        }
    }

    /**
     * 校验角色是否有数据权限
     *
     * @param roleId 角色id
     */
    @Override
    public void checkRoleDataScope(Long roleId) {
        if (!AdminChecker.isAdminUser(tokenService.getUserId())) {
            final var role = new SysRole();
            role.setRoleId(roleId);
            final var roles = selectRoleList(role);
            if (CollUtil.isEmpty(roles)) {
                throw new ServiceException("没有权限访问角色数据！");
            }
        }
    }

    /**
     * 通过角色ID查询角色使用数量
     *
     * @param roleId 角色ID
     * @return 结果
     */
    @Override
    public Integer countUserRoleByRoleId(Long roleId) {
        return userRoleMapper.countUserRoleByRoleId(roleId);
    }

    /**
     * 新增保存角色信息
     *
     * @param role 角色信息
     * @return 结果
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean insertRole(SysRole role) {
        // 新增角色信息
        roleMapper.insertRole(role);
        return SqlHelper.retBool(insertRoleMenu(role));
    }

    /**
     * 修改保存角色信息
     *
     * @param role 角色信息
     * @return 结果
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean updateRole(SysRole role) {
        // 修改角色信息
        roleMapper.updateRole(role);
        // 删除角色与菜单关联
        roleMenuMapper.deleteRoleMenuByRoleId(role.getRoleId());
        return SqlHelper.retBool(insertRoleMenu(role));
    }

    /**
     * 修改角色状态
     *
     * @param role 角色信息
     * @return 结果
     */
    @Override
    public Boolean updateRoleStatus(SysRole role) {
        return SqlHelper.retBool(roleMapper.updateRole(role));
    }

    /**
     * 修改数据权限信息
     *
     * @param role 角色信息
     * @return 结果
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean authDataScope(SysRole role) {
        // 修改角色信息
        roleMapper.updateRole(role);
        // 删除角色与部门关联
        roleDeptMapper.deleteRoleDeptByRoleId(role.getRoleId());
        // 新增角色和部门信息（数据权限）
        return SqlHelper.retBool(insertRoleDept(role));
    }

    /**
     * 新增角色菜单信息
     *
     * @param role 角色对象
     */
    public Integer insertRoleMenu(SysRole role) {
        Integer rows = 1;
        // 新增用户与角色管理
        final var list = new ArrayList<SysRoleMenu>();
        for (final var menuId : role.getMenuIds()) {
            final var rm = new SysRoleMenu();
            rm.setRoleId(role.getRoleId());
            rm.setMenuId(menuId);
            list.add(rm);
        }
        if (!list.isEmpty()) {
            rows = roleMenuMapper.batchRoleMenu(list);
        }
        return rows;
    }

    /**
     * 新增角色部门信息(数据权限)
     *
     * @param role 角色对象
     */
    public Integer insertRoleDept(SysRole role) {
        Integer rows = 1;
        // 新增角色与部门（数据权限）管理
        final var list = new ArrayList<SysRoleDept>();
        for (final var deptId : role.getDeptIds()) {
            final var rd = new SysRoleDept();
            rd.setRoleId(role.getRoleId());
            rd.setDeptId(deptId);
            list.add(rd);
        }
        if (!list.isEmpty()) {
            rows = roleDeptMapper.batchRoleDept(list);
        }
        return rows;
    }

    /**
     * 通过角色ID删除角色
     *
     * @param roleId 角色ID
     * @return 结果
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Integer deleteRoleById(Long roleId) {
        // 删除角色与菜单关联
        roleMenuMapper.deleteRoleMenuByRoleId(roleId);
        // 删除角色与部门关联
        roleDeptMapper.deleteRoleDeptByRoleId(roleId);
        return roleMapper.deleteRoleById(roleId);
    }

    /**
     * 批量删除角色信息
     *
     * @param roleIds 需要删除的角色ID
     * @return 结果
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean deleteRoleByIds(Long[] roleIds) {
        for (final var roleId : roleIds) {
            final var systemRole = new SysRole();
            systemRole.setRoleId(roleId);
            checkRoleAllowed(systemRole);
            checkRoleDataScope(roleId);
            final var role = selectRoleById(roleId);
            if (countUserRoleByRoleId(roleId) > 0) {
                throw new ServiceException(String.format("%1$s已分配,不能删除", role.getRoleName()));
            }
        }
        // 删除角色与菜单关联
        roleMenuMapper.deleteRoleMenu(roleIds);
        // 删除角色与部门关联
        roleDeptMapper.deleteRoleDept(roleIds);
        return SqlHelper.retBool(roleMapper.deleteRoleByIds(roleIds));
    }

    /**
     * 取消授权用户角色
     *
     * @param userRole 用户和角色关联信息
     * @return 结果
     */
    @Override
    public Boolean deleteAuthUser(SysUserRole userRole) {
        return SqlHelper.retBool(userRoleMapper.deleteUserRoleInfo(userRole));
    }

    /**
     * 批量取消授权用户角色
     *
     * @param roleId  角色ID
     * @param userIds 需要取消授权的用户数据ID
     * @return 结果
     */
    @Override
    public Boolean deleteAuthUsers(Long roleId, Long[] userIds) {
        return SqlHelper.retBool(userRoleMapper.deleteUserRoleInfos(roleId, userIds));
    }

    /**
     * 批量选择授权用户角色
     *
     * @param roleId  角色ID
     * @param userIds 需要授权的用户数据ID
     * @return 结果
     */
    @Override
    public Boolean insertAuthUsers(Long roleId, Long[] userIds) {
        // 新增用户与角色管理
        final var list = new ArrayList<SysUserRole>();
        for (final var userId : userIds) {
            final var ur = new SysUserRole();
            ur.setUserId(userId);
            ur.setRoleId(roleId);
            list.add(ur);
        }
        return SqlHelper.retBool(userRoleMapper.batchUserRole(list));
    }
}