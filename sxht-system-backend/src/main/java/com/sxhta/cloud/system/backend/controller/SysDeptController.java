package com.sxhta.cloud.system.backend.controller;


import com.sxhta.cloud.common.constant.UserConstants;
import com.sxhta.cloud.common.web.controller.BaseController;
import com.sxhta.cloud.common.web.domain.CommonResponse;
import com.sxhta.cloud.log.annotation.Log;
import com.sxhta.cloud.log.enums.BusinessType;
import com.sxhta.cloud.remote.domain.SysDept;
import com.sxhta.cloud.remote.domain.SysUser;
import com.sxhta.cloud.remote.model.SystemUserCacheVo;
import com.sxhta.cloud.security.annotation.RequiresPermissions;
import com.sxhta.cloud.security.service.TokenService;
import com.sxhta.cloud.system.backend.service.ISysDeptService;
import jakarta.inject.Inject;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 部门信息
 */
@RestController
@RequestMapping("/dept")
public class SysDeptController extends BaseController {

    @Inject
    private ISysDeptService deptService;

    @Inject
    private TokenService<SystemUserCacheVo, SysUser> tokenService;

    /**
     * 获取部门列表
     */
    @RequiresPermissions("system:dept:list")
    @GetMapping("/list")
    public CommonResponse<List<SysDept>> list(SysDept dept) {
        final var depts = deptService.selectDeptList(dept);
        return CommonResponse.success(depts);
    }

    /**
     * 查询部门列表（排除节点）
     */
    @RequiresPermissions("system:dept:list")
    @GetMapping("/list/exclude/{deptId}")
    public CommonResponse<List<SysDept>> excludeChild(@PathVariable(value = "deptId", required = false) Long deptId) {
        final var depts = deptService.selectDeptList(new SysDept());
        depts.removeIf(d -> d.getDeptId().intValue() == deptId || ArrayUtils.contains(StringUtils.split(d.getAncestors(), ","), deptId + ""));
        return CommonResponse.success(depts);
    }

    /**
     * 根据部门编号获取详细信息
     */
    @RequiresPermissions("system:dept:query")
    @GetMapping(value = "/{deptId}")
    public CommonResponse<SysDept> getInfo(@PathVariable Long deptId) {
        deptService.checkDeptDataScope(deptId);
        return CommonResponse.success(deptService.selectDeptById(deptId));
    }

    /**
     * 新增部门
     */
    @RequiresPermissions("system:dept:add")
    @Log(title = "部门管理", businessType = BusinessType.INSERT)
    @PostMapping
    public CommonResponse<Boolean> add(@Validated @RequestBody SysDept dept) {
        if (!deptService.checkDeptNameUnique(dept)) {
            return CommonResponse.error("新增部门'" + dept.getDeptName() + "'失败，部门名称已存在");
        }
        dept.setCreateBy(tokenService.getUsername());
        return CommonResponse.result(deptService.insertDept(dept));
    }

    /**
     * 修改部门
     */
    @RequiresPermissions("system:dept:edit")
    @Log(title = "部门管理", businessType = BusinessType.UPDATE)
    @PutMapping
    public CommonResponse<Boolean> edit(@Validated @RequestBody SysDept dept) {
        final var deptId = dept.getDeptId();
        deptService.checkDeptDataScope(deptId);
        if (!deptService.checkDeptNameUnique(dept)) {
            return CommonResponse.error("修改部门'" + dept.getDeptName() + "'失败，部门名称已存在");
        } else if (dept.getParentId().equals(deptId)) {
            return CommonResponse.error("修改部门'" + dept.getDeptName() + "'失败，上级部门不能是自己");
        } else if (StringUtils.equals(UserConstants.DEPT_DISABLE, dept.getStatus()) && deptService.selectNormalChildrenDeptById(deptId) > 0) {
            return CommonResponse.error("该部门包含未停用的子部门！");
        }
        dept.setUpdateBy(tokenService.getUsername());
        return CommonResponse.result(deptService.updateDept(dept));
    }

    /**
     * 删除部门
     */
    @RequiresPermissions("system:dept:remove")
    @Log(title = "部门管理", businessType = BusinessType.DELETE)
    @DeleteMapping("/{deptId}")
    public CommonResponse<Boolean> remove(@PathVariable Long deptId) {
        if (deptService.hasChildByDeptId(deptId)) {
            return CommonResponse.warn("存在下级部门,不允许删除");
        }
        if (deptService.checkDeptExistUser(deptId)) {
            return  CommonResponse.warn("部门存在用户,不允许删除");
        }
        deptService.checkDeptDataScope(deptId);
        return  CommonResponse.result(deptService.deleteDeptById(deptId));
    }
}
