package com.sxhta.cloud.system.backend.controller;


import com.sxhta.cloud.common.utils.poi.ExcelUtil;
import com.sxhta.cloud.common.web.controller.BaseController;
import com.sxhta.cloud.common.web.domain.CommonResponse;
import com.sxhta.cloud.common.web.page.PageRequest;
import com.sxhta.cloud.common.web.page.TableDataInfo;
import com.sxhta.cloud.log.annotation.Log;
import com.sxhta.cloud.log.enums.BusinessType;
import com.sxhta.cloud.remote.domain.SysDept;
import com.sxhta.cloud.remote.domain.SysRole;
import com.sxhta.cloud.remote.domain.SysUser;
import com.sxhta.cloud.remote.model.SystemUserCacheVo;
import com.sxhta.cloud.security.annotation.RequiresPermissions;
import com.sxhta.cloud.security.service.TokenService;
import com.sxhta.cloud.system.backend.domain.SysUserRole;
import com.sxhta.cloud.system.backend.response.DeptTreeResponse;
import com.sxhta.cloud.system.backend.service.ISysDeptService;
import com.sxhta.cloud.system.backend.service.ISysRoleService;
import com.sxhta.cloud.system.backend.service.ISysUserService;
import jakarta.inject.Inject;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 角色信息
 */
@RestController
@RequestMapping("/role")
public class SysRoleController extends BaseController {

    @Inject
    private ISysRoleService roleService;

    @Inject
    private ISysUserService userService;

    @Inject
    private ISysDeptService deptService;

    @Inject
    private TokenService<SystemUserCacheVo, SysUser> tokenService;

    @RequiresPermissions("system:role:list")
    @GetMapping("/list")
    public TableDataInfo<SysRole> list(SysRole role, PageRequest pageRequest) {
        startPage(pageRequest);
        final var list = roleService.selectRoleList(role);
        return CommonResponse.list(list);
    }

    @Log(title = "角色管理", businessType = BusinessType.EXPORT)
    @RequiresPermissions("system:role:export")
    @PostMapping("/export")
    public void export(HttpServletResponse response, SysRole role) {
        final var list = roleService.selectRoleList(role);
        final var util = new ExcelUtil<>(SysRole.class);
        util.exportExcel(response, list, "角色数据");
    }

    /**
     * 根据角色编号获取详细信息
     */
    @RequiresPermissions("system:role:query")
    @GetMapping(value = "/{roleId}")
    public CommonResponse<SysRole> getInfo(@PathVariable Long roleId) {
        roleService.checkRoleDataScope(roleId);
        return CommonResponse.success(roleService.selectRoleById(roleId));
    }

    /**
     * 新增角色
     */
    @RequiresPermissions("system:role:add")
    @Log(title = "角色管理", businessType = BusinessType.INSERT)
    @PostMapping
    public CommonResponse<Boolean> add(@Validated @RequestBody SysRole role) {
        if (!roleService.checkRoleNameUnique(role)) {
            return CommonResponse.error("新增角色'" + role.getRoleName() + "'失败，角色名称已存在");
        } else if (!roleService.checkRoleKeyUnique(role)) {
            return CommonResponse.error("新增角色'" + role.getRoleName() + "'失败，角色权限已存在");
        }
        role.setCreateBy(tokenService.getUsername());
        return CommonResponse.result(roleService.insertRole(role));

    }

    /**
     * 修改保存角色
     */
    @RequiresPermissions("system:role:edit")
    @Log(title = "角色管理", businessType = BusinessType.UPDATE)
    @PutMapping
    public CommonResponse<Boolean> edit(@Validated @RequestBody SysRole role) {
        roleService.checkRoleAllowed(role);
        roleService.checkRoleDataScope(role.getRoleId());
        if (!roleService.checkRoleNameUnique(role)) {
            return CommonResponse.error("修改角色'" + role.getRoleName() + "'失败，角色名称已存在");
        } else if (!roleService.checkRoleKeyUnique(role)) {
            return CommonResponse.error("修改角色'" + role.getRoleName() + "'失败，角色权限已存在");
        }
        role.setUpdateBy(tokenService.getUsername());
        return CommonResponse.result(roleService.updateRole(role));
    }

    /**
     * 修改保存数据权限
     */
    @RequiresPermissions("system:role:edit")
    @Log(title = "角色管理", businessType = BusinessType.UPDATE)
    @PutMapping("/dataScope")
    public CommonResponse<Boolean> dataScope(@RequestBody SysRole role) {
        roleService.checkRoleAllowed(role);
        roleService.checkRoleDataScope(role.getRoleId());
        return CommonResponse.result(roleService.authDataScope(role));
    }

    /**
     * 状态修改
     */
    @RequiresPermissions("system:role:edit")
    @Log(title = "角色管理", businessType = BusinessType.UPDATE)
    @PutMapping("/changeStatus")
    public CommonResponse<Boolean> changeStatus(@RequestBody SysRole role) {
        roleService.checkRoleAllowed(role);
        roleService.checkRoleDataScope(role.getRoleId());
        role.setUpdateBy(tokenService.getUsername());
        return CommonResponse.result(roleService.updateRoleStatus(role));
    }

    /**
     * 删除角色
     */
    @RequiresPermissions("system:role:remove")
    @Log(title = "角色管理", businessType = BusinessType.DELETE)
    @DeleteMapping("/{roleIds}")
    public CommonResponse<Boolean> remove(@PathVariable Long[] roleIds) {
        return CommonResponse.result(roleService.deleteRoleByIds(roleIds));
    }

    /**
     * 获取角色选择框列表
     */
    @RequiresPermissions("system:role:query")
    @GetMapping("/optionselect")
    public CommonResponse<List<SysRole>> optionselect() {
        return CommonResponse.success(roleService.selectRoleAll());
    }

    /**
     * 查询已分配用户角色列表
     */
    @RequiresPermissions("system:role:list")
    @GetMapping("/authUser/allocatedList")
    public TableDataInfo<SysUser> allocatedList(SysUser user, PageRequest pageRequest) {
        startPage(pageRequest);
        final var list = userService.selectAllocatedList(user);
        return CommonResponse.list(list);
    }

    /**
     * 查询未分配用户角色列表
     */
    @RequiresPermissions("system:role:list")
    @GetMapping("/authUser/unallocatedList")
    public TableDataInfo<SysUser> unallocatedList(SysUser user, PageRequest pageRequest) {
        startPage(pageRequest);
        final var list = userService.selectUnallocatedList(user);
        return CommonResponse.list(list);
    }

    /**
     * 取消授权用户
     */
    @RequiresPermissions("system:role:edit")
    @Log(title = "角色管理", businessType = BusinessType.GRANT)
    @PutMapping("/authUser/cancel")
    public CommonResponse<Boolean> cancelAuthUser(@RequestBody SysUserRole userRole) {
        return CommonResponse.result(roleService.deleteAuthUser(userRole));
    }

    /**
     * 批量取消授权用户
     */
    @RequiresPermissions("system:role:edit")
    @Log(title = "角色管理", businessType = BusinessType.GRANT)
    @PutMapping("/authUser/cancelAll")
    public CommonResponse<Boolean> cancelAuthUserAll(Long roleId, Long[] userIds) {
        return CommonResponse.result(roleService.deleteAuthUsers(roleId, userIds));
    }

    /**
     * 批量选择用户授权
     */
    @RequiresPermissions("system:role:edit")
    @Log(title = "角色管理", businessType = BusinessType.GRANT)
    @PutMapping("/authUser/selectAll")
    public CommonResponse<Boolean> selectAuthUserAll(Long roleId, Long[] userIds) {
        roleService.checkRoleDataScope(roleId);
        return CommonResponse.result(roleService.insertAuthUsers(roleId, userIds));
    }

    /**
     * 获取对应角色部门树列表
     */
    @RequiresPermissions("system:role:query")
    @GetMapping(value = "/deptTree/{roleId}")
    public CommonResponse<DeptTreeResponse> deptTree(@PathVariable("roleId") Long roleId) {
        final var response = new DeptTreeResponse();
        response.setDepts(deptService.selectDeptTreeList(new SysDept()))
                .setCheckedKeys(deptService.selectDeptListByRoleId(roleId));
        return CommonResponse.success(response);
    }
}