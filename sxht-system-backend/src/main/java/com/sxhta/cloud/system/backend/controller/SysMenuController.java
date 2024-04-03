package com.sxhta.cloud.system.backend.controller;


import com.sxhta.cloud.common.constant.UserConstants;
import com.sxhta.cloud.common.utils.CommonStringUtil;
import com.sxhta.cloud.common.web.controller.BaseController;
import com.sxhta.cloud.common.web.domain.CommonResponse;
import com.sxhta.cloud.log.annotation.Log;
import com.sxhta.cloud.log.enums.BusinessType;
import com.sxhta.cloud.remote.domain.SysUser;
import com.sxhta.cloud.remote.vo.SystemUserCacheVo;
import com.sxhta.cloud.security.annotation.RequiresPermissions;
import com.sxhta.cloud.security.service.TokenService;
import com.sxhta.cloud.system.backend.domain.SysMenu;
import com.sxhta.cloud.system.backend.domain.vo.RouterVo;
import com.sxhta.cloud.system.backend.domain.vo.TreeSelect;
import com.sxhta.cloud.system.backend.response.MenuTreeResponse;
import com.sxhta.cloud.system.backend.service.ISysMenuService;
import jakarta.inject.Inject;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 菜单信息
 */
@RestController
@RequestMapping("/menu")
public class SysMenuController extends BaseController {
    @Inject
    private ISysMenuService menuService;

    @Inject
    private TokenService<SystemUserCacheVo, SysUser> tokenService;

    /**
     * 获取菜单列表
     */
    @RequiresPermissions("system:menu:list")
    @GetMapping("/list")
    public CommonResponse<List<SysMenu>> list(SysMenu menu) {
        final var userId = tokenService.getUserId();
        final var menus = menuService.selectMenuList(menu, userId);
        return CommonResponse.success(menus);
    }

    /**
     * 根据菜单编号获取详细信息
     */
    @RequiresPermissions("system:menu:query")
    @GetMapping(value = "/{menuId}")
    public CommonResponse<SysMenu> getInfo(@PathVariable Long menuId) {
        System.out.println("菜单的信息");
        return CommonResponse.success(menuService.selectMenuById(menuId));
    }

    /**
     * 获取菜单下拉树列表
     */
    @GetMapping("/treeselect")
    public CommonResponse<List<TreeSelect>> treeSelect(SysMenu menu) {
        final var userId = tokenService.getUserId();
        final var menus = menuService.selectMenuList(menu, userId);
        return CommonResponse.success(menuService.buildMenuTreeSelect(menus));
    }

    /**
     * 加载对应角色菜单列表树
     */
    @GetMapping(value = "/roleMenuTreeselect/{roleId}")
    public CommonResponse<MenuTreeResponse> roleMenuTreeSelect(@PathVariable("roleId") Long roleId) {
        final var userId = tokenService.getUserId();
        final var menus = menuService.selectMenuList(userId);
        final var response = new MenuTreeResponse();
        response.setCheckedKeys(menuService.selectMenuListByRoleId(roleId))
                .setMenus(menuService.buildMenuTreeSelect(menus));
        return CommonResponse.success(response);
    }

    /**
     * 新增菜单
     */
    @RequiresPermissions("system:menu:add")
    @Log(title = "菜单管理", businessType = BusinessType.INSERT)
    @PostMapping
    public CommonResponse<Boolean> add(@Validated @RequestBody SysMenu menu) {
        if (!menuService.checkMenuNameUnique(menu)) {
            return CommonResponse.error("新增菜单'" + menu.getMenuName() + "'失败，菜单名称已存在");
        } else if (UserConstants.YES_FRAME.equals(menu.getIsFrame()) && !CommonStringUtil.isHttp(menu.getPath())) {
            return CommonResponse.error("新增菜单'" + menu.getMenuName() + "'失败，地址必须以http(s)://开头");
        }
        menu.setCreateBy(tokenService.getUsername());
        return CommonResponse.result(menuService.insertMenu(menu));
    }

    /**
     * 修改菜单
     */
    @RequiresPermissions("system:menu:edit")
    @Log(title = "菜单管理", businessType = BusinessType.UPDATE)
    @PutMapping
    public CommonResponse<Boolean> edit(@Validated @RequestBody SysMenu menu) {
        if (!menuService.checkMenuNameUnique(menu)) {
            return CommonResponse.error("修改菜单'" + menu.getMenuName() + "'失败，菜单名称已存在");
        } else if (UserConstants.YES_FRAME.equals(menu.getIsFrame()) && !CommonStringUtil.isHttp(menu.getPath())) {
            return CommonResponse.error("修改菜单'" + menu.getMenuName() + "'失败，地址必须以http(s)://开头");
        } else if (menu.getMenuId().equals(menu.getParentId())) {
            return CommonResponse.error("修改菜单'" + menu.getMenuName() + "'失败，上级菜单不能选择自己");
        }
        menu.setUpdateBy(tokenService.getUsername());
        return CommonResponse.result(menuService.updateMenu(menu));
    }

    /**
     * 删除菜单
     */
    @RequiresPermissions("system:menu:remove")
    @Log(title = "菜单管理", businessType = BusinessType.DELETE)
    @DeleteMapping("/{menuId}")
    public CommonResponse<Boolean> remove(@PathVariable("menuId") Long menuId) {
        if (menuService.hasChildByMenuId(menuId)) {
            return CommonResponse.warn("存在子菜单,不允许删除");
        }
        if (menuService.checkMenuExistRole(menuId)) {
            return CommonResponse.warn("菜单已分配,不允许删除");
        }
        return CommonResponse.result(menuService.deleteMenuById(menuId));
    }

    /**
     * 获取路由信息
     *
     * @return 路由信息
     */
    @GetMapping("getRouters")
    public CommonResponse<List<RouterVo>> getRouters() {
        final var userId = tokenService.getUserId();
        final var menus = menuService.selectMenuTreeByUserId(userId);
        return CommonResponse.success(menuService.buildMenus(menus));
    }
}