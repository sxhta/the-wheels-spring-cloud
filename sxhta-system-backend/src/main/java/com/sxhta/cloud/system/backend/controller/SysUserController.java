package com.sxhta.cloud.system.backend.controller;


import cn.hutool.core.util.ObjectUtil;
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
import com.sxhta.cloud.remote.vo.SystemUserCacheVo;
import com.sxhta.cloud.remote.util.AdminChecker;
import com.sxhta.cloud.security.annotation.InnerAuth;
import com.sxhta.cloud.security.annotation.RequiresPermissions;
import com.sxhta.cloud.security.service.SecurityService;
import com.sxhta.cloud.security.service.TokenService;
import com.sxhta.cloud.system.backend.domain.vo.TreeSelect;
import com.sxhta.cloud.system.backend.response.UserDetailsResponse;
import com.sxhta.cloud.system.backend.response.UserInfoResponse;
import com.sxhta.cloud.system.backend.response.UserRolesResponse;
import com.sxhta.cloud.system.backend.service.*;
import jakarta.inject.Inject;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 用户信息
 */
@RestController
@RequestMapping("/user")
public class SysUserController extends BaseController {
    @Inject
    private ISysUserService userService;

    @Inject
    private ISysRoleService roleService;

    @Inject
    private ISysDeptService deptService;

    @Inject
    private ISysPostService postService;

    @Inject
    private ISysPermissionService permissionService;

    @Inject
    private SysConfigService configService;

    @Inject
    private TokenService<SystemUserCacheVo, SysUser> tokenService;

    @Inject
    private SecurityService securityService;

    /**
     * 获取用户列表
     */
    @RequiresPermissions("system:user:list")
    @GetMapping("/list")
    public TableDataInfo<SysUser> list(SysUser user, PageRequest pageRequest) {
        startPage(pageRequest);
        final var list = userService.selectUserList(user);
        return CommonResponse.list(list);
    }

    @Log(title = "用户管理", businessType = BusinessType.EXPORT)
    @RequiresPermissions("system:user:export")
    @PostMapping("/export")
    public void export(HttpServletResponse response, SysUser user) {
        final var list = userService.selectUserList(user);
        final var util = new ExcelUtil<>(SysUser.class);
        util.exportExcel(response, list, "用户数据");
    }

    @Log(title = "用户管理", businessType = BusinessType.IMPORT)
    @RequiresPermissions("system:user:import")
    @PostMapping("/importData")
    public CommonResponse<Boolean> importData(MultipartFile file, Boolean updateSupport) throws Exception {
        final var util = new ExcelUtil<>(SysUser.class);
        final var userList = util.importExcel(file.getInputStream());
        final var operName = tokenService.getUsername();
        final var message = userService.importUser(userList, updateSupport, operName);
        return CommonResponse.success(message);
    }

    @PostMapping("/importTemplate")
    public void importTemplate(HttpServletResponse response) {
        final var util = new ExcelUtil<>(SysUser.class);
        util.importTemplateExcel(response, "用户数据");
    }

    /**
     * 获取当前用户信息
     */
    @InnerAuth
    @GetMapping("/info/{username}")
    public CommonResponse<SystemUserCacheVo> info(@PathVariable("username") String username) {
        final var sysUser = userService.selectUserByUserName(username);
        if (ObjectUtil.isNull(sysUser)) {
            return CommonResponse.error("用户名或密码错误");
        }
        // 角色集合
        final var roles = permissionService.getRolePermission(sysUser);
        // 权限集合
        final var permissions = permissionService.getMenuPermission(sysUser);
        final var sysUserVo = new SystemUserCacheVo();
        sysUserVo.setUserEntity(sysUser);
        sysUserVo.setRoles(roles);
        sysUserVo.setPermissions(permissions);
        return CommonResponse.success(sysUserVo);
    }

    /**
     * 注册用户信息
     */
    @InnerAuth
    @PostMapping("/register")
    public CommonResponse<Boolean> register(@RequestBody SysUser sysUser) {
        final var username = sysUser.getUserName();
        if (!("true".equals(configService.selectConfigByKey("sys.account.registerUser")))) {
            return CommonResponse.error("当前系统没有开启注册功能！");
        }
        if (!userService.checkUserNameUnique(sysUser)) {
            return CommonResponse.error("保存用户'" + username + "'失败，注册账号已存在");
        }
        return CommonResponse.success(userService.registerUser(sysUser));
    }

    /**
     * 获取用户信息
     *
     * @return 用户信息
     */
    @GetMapping("getInfo")
    public CommonResponse<UserInfoResponse> getInfo() {
        final var user = userService.selectUserById(tokenService.getUserId());
        // 角色集合
        final var roles = permissionService.getRolePermission(user);
        // 权限集合
        final var permissions = permissionService.getMenuPermission(user);
        final var response = new UserInfoResponse();
        response.setUser(user)
                .setRoles(roles)
                .setPermissions(permissions);
        return CommonResponse.success(response);
    }

    /**
     * 根据用户编号获取详细信息
     */
    @RequiresPermissions("system:user:query")
    @GetMapping(value = {"/", "/{userId}"})
    public CommonResponse<UserDetailsResponse> getInfo(@PathVariable(value = "userId", required = false) Long userId) {
        userService.checkUserDataScope(userId);
        final var roles = roleService.selectRoleAll();
        final var response = new UserDetailsResponse();
        final var resultsRoles = AdminChecker.isAdminUser(userId) ? roles : roles.stream().filter(r -> !AdminChecker.isAdminRole(r.getRoleId())).collect(Collectors.toList());
        final var posts = postService.selectPostAll();
        response.setRoles(resultsRoles)
                .setPosts(posts);
        if (ObjectUtil.isNotNull(userId)) {
            final var sysUser = userService.selectUserById(userId);
            final var postIds = postService.selectPostListByUserId(userId);
            final var resultRoles = sysUser.getRoles().stream().map(SysRole::getRoleId).collect(Collectors.toList());
            response.setUser(sysUser)
                    .setPostIds(postIds)
                    .setRoleIds(resultRoles);
        }
        return CommonResponse.success(response);
    }

    /**
     * 新增用户
     */
    @RequiresPermissions("system:user:add")
    @Log(title = "用户管理", businessType = BusinessType.INSERT)
    @PostMapping
    public CommonResponse<Boolean> add(@Validated @RequestBody SysUser user) {
        if (!userService.checkUserNameUnique(user)) {
            return CommonResponse.error("新增用户'" + user.getUserName() + "'失败，登录账号已存在");
        } else if (ObjectUtil.isNotEmpty(user.getPhonenumber()) && !userService.checkPhoneUnique(user)) {
            return CommonResponse.error("新增用户'" + user.getUserName() + "'失败，手机号码已存在");
        } else if (ObjectUtil.isNotEmpty(user.getEmail()) && !userService.checkEmailUnique(user)) {
            return CommonResponse.error("新增用户'" + user.getUserName() + "'失败，邮箱账号已存在");
        }
        user.setCreateBy(tokenService.getUsername());
        user.setPassword(securityService.encryptPassword(user.getPassword()));
        return CommonResponse.result(userService.insertUser(user));
    }

    /**
     * 修改用户
     */
    @RequiresPermissions("system:user:edit")
    @Log(title = "用户管理", businessType = BusinessType.UPDATE)
    @PutMapping
    public CommonResponse<Boolean> edit(@Validated @RequestBody SysUser user) {
        userService.checkUserAllowed(user);
        userService.checkUserDataScope(user.getUserId());
        if (!userService.checkUserNameUnique(user)) {
            return CommonResponse.error("修改用户'" + user.getUserName() + "'失败，登录账号已存在");
        } else if (ObjectUtil.isNotEmpty(user.getPhonenumber()) && !userService.checkPhoneUnique(user)) {
            return CommonResponse.error("修改用户'" + user.getUserName() + "'失败，手机号码已存在");
        } else if (ObjectUtil.isNotEmpty(user.getEmail()) && !userService.checkEmailUnique(user)) {
            return CommonResponse.error("修改用户'" + user.getUserName() + "'失败，邮箱账号已存在");
        }
        user.setUpdateBy(tokenService.getUsername());
        return CommonResponse.result(userService.updateUser(user));
    }

    /**
     * 删除用户
     */
    @RequiresPermissions("system:user:remove")
    @Log(title = "用户管理", businessType = BusinessType.DELETE)
    @DeleteMapping("/{userIds}")
    public CommonResponse<Boolean> remove(@PathVariable Long[] userIds) {
        if (ArrayUtils.contains(userIds, tokenService.getUserId())) {
            return CommonResponse.error("当前用户不能删除");
        }
        return CommonResponse.result(userService.deleteUserByIds(userIds));
    }

    /**
     * 重置密码
     */
    @RequiresPermissions("system:user:edit")
    @Log(title = "用户管理", businessType = BusinessType.UPDATE)
    @PutMapping("/resetPwd")
    public CommonResponse<Boolean> resetPwd(@RequestBody SysUser user) {
        userService.checkUserAllowed(user);
        userService.checkUserDataScope(user.getUserId());
        user.setPassword(securityService.encryptPassword(user.getPassword()));
        user.setUpdateBy(tokenService.getUsername());
        return CommonResponse.result(userService.resetPwd(user));
    }

    /**
     * 状态修改
     */
    @RequiresPermissions("system:user:edit")
    @Log(title = "用户管理", businessType = BusinessType.UPDATE)
    @PutMapping("/changeStatus")
    public CommonResponse<Boolean> changeStatus(@RequestBody SysUser user) {
        userService.checkUserAllowed(user);
        userService.checkUserDataScope(user.getUserId());
        user.setUpdateBy(tokenService.getUsername());
        return CommonResponse.result(userService.updateUserStatus(user));
    }

    /**
     * 根据用户编号获取授权角色
     */
    @RequiresPermissions("system:user:query")
    @GetMapping("/authRole/{userId}")
    public CommonResponse<UserRolesResponse> authRole(@PathVariable("userId") Long userId) {
        final var user = userService.selectUserById(userId);
        final var roles = roleService.selectRolesByUserId(userId);
        final var response = new UserRolesResponse();
        final var resultRoles = AdminChecker.isAdminUser(userId) ? roles : roles.stream().filter(r -> !AdminChecker.isAdminRole(r.getRoleId())).collect(Collectors.toList());
        response.setUser(user)
                .setRoles(resultRoles);
        return CommonResponse.success(response);
    }

    /**
     * 用户授权角色
     */
    @RequiresPermissions("system:user:edit")
    @Log(title = "用户管理", businessType = BusinessType.GRANT)
    @PutMapping("/authRole")
    public CommonResponse<Boolean> insertAuthRole(Long userId, Long[] roleIds) {
        userService.checkUserDataScope(userId);
        userService.insertUserAuth(userId, roleIds);
        return CommonResponse.success();
    }

    /**
     * 获取部门树列表
     */
    @RequiresPermissions("system:user:list")
    @GetMapping("/deptTree")
    public CommonResponse<List<TreeSelect>> deptTree(SysDept dept) {
        return CommonResponse.success(deptService.selectDeptTreeList(dept));
    }
}
