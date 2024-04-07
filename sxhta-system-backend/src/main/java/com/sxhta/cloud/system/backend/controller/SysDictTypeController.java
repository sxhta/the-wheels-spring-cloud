package com.sxhta.cloud.system.backend.controller;


import com.sxhta.cloud.common.utils.poi.ExcelUtil;
import com.sxhta.cloud.common.web.controller.BaseController;
import com.sxhta.cloud.common.web.domain.CommonResponse;
import com.sxhta.cloud.common.web.page.PageRequest;
import com.sxhta.cloud.common.web.page.TableDataInfo;
import com.sxhta.cloud.log.annotation.Log;
import com.sxhta.cloud.log.enums.BusinessType;
import com.sxhta.cloud.remote.domain.SysDictType;
import com.sxhta.cloud.remote.domain.SysUser;
import com.sxhta.cloud.remote.vo.SystemUserCacheVo;
import com.sxhta.cloud.security.annotation.RequiresPermissions;
import com.sxhta.cloud.security.service.TokenService;
import com.sxhta.cloud.system.backend.service.ISysDictTypeService;
import jakarta.inject.Inject;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 数据字典信息
 */
@RestController
@RequestMapping("/dict/type")
public class SysDictTypeController extends BaseController {
    @Inject
    private ISysDictTypeService dictTypeService;

    @Inject
    private TokenService<SystemUserCacheVo, SysUser> tokenService;

    @RequiresPermissions("system:dict:list")
    @GetMapping("/list")
    public TableDataInfo<SysDictType> list(SysDictType dictType, PageRequest pageRequest) {
        startPage(pageRequest);
        final var list = dictTypeService.selectDictTypeList(dictType);
        return CommonResponse.list(list);
    }

    @Log(title = "字典类型", businessType = BusinessType.EXPORT)
    @RequiresPermissions("system:dict:export")
    @PostMapping("/export")
    public void export(HttpServletResponse response, SysDictType dictType) {
        final var list = dictTypeService.selectDictTypeList(dictType);
        final var util = new ExcelUtil<>(SysDictType.class);
        util.exportExcel(response, list, "字典类型");
    }

    /**
     * 查询字典类型详细
     */
    @RequiresPermissions("system:dict:query")
    @GetMapping(value = "/{dictId}")
    public CommonResponse<SysDictType> getInfo(@PathVariable Long dictId) {
        return CommonResponse.success(dictTypeService.selectDictTypeById(dictId));
    }

    /**
     * 新增字典类型
     */
    @RequiresPermissions("system:dict:add")
    @Log(title = "字典类型", businessType = BusinessType.INSERT)
    @PostMapping
    public CommonResponse<Boolean> add(@Validated @RequestBody SysDictType dict) {
        if (!dictTypeService.checkDictTypeUnique(dict)) {
            return CommonResponse.error("新增字典'" + dict.getDictName() + "'失败，字典类型已存在");
        }
        dict.setCreateBy(tokenService.getUsername());
        return CommonResponse.result(dictTypeService.insertDictType(dict));
    }

    /**
     * 修改字典类型
     */
    @RequiresPermissions("system:dict:edit")
    @Log(title = "字典类型", businessType = BusinessType.UPDATE)
    @PutMapping
    public CommonResponse<Boolean> edit(@Validated @RequestBody SysDictType dict) {
        if (!dictTypeService.checkDictTypeUnique(dict)) {
            return CommonResponse.error("修改字典'" + dict.getDictName() + "'失败，字典类型已存在");
        }
        dict.setUpdateBy(tokenService.getUsername());
        return CommonResponse.result(dictTypeService.updateDictType(dict));
    }

    /**
     * 删除字典类型
     */
    @RequiresPermissions("system:dict:remove")
    @Log(title = "字典类型", businessType = BusinessType.DELETE)
    @DeleteMapping("/{dictIds}")
    public CommonResponse<Boolean> remove(@PathVariable Long[] dictIds) {
        dictTypeService.deleteDictTypeByIds(dictIds);
        return CommonResponse.success();
    }

    /**
     * 刷新字典缓存
     */
    @RequiresPermissions("system:dict:remove")
    @Log(title = "字典类型", businessType = BusinessType.CLEAN)
    @DeleteMapping("/refreshCache")
    public CommonResponse<Boolean> refreshCache() {
        dictTypeService.resetDictCache();
        return CommonResponse.success();
    }

    /**
     * 获取字典选择框列表
     */
    @GetMapping("/optionselect")
    public CommonResponse<List<SysDictType>> optionselect() {
        final var dictTypes = dictTypeService.selectDictTypeAll();
        return CommonResponse.success(dictTypes);
    }
}
