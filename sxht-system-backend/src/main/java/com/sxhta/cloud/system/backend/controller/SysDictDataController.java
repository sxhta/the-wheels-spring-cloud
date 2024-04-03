package com.sxhta.cloud.system.backend.controller;


import cn.hutool.core.util.ObjectUtil;
import com.sxhta.cloud.common.utils.poi.ExcelUtil;
import com.sxhta.cloud.common.web.controller.BaseController;
import com.sxhta.cloud.common.web.domain.CommonResponse;
import com.sxhta.cloud.common.web.page.PageRequest;
import com.sxhta.cloud.common.web.page.TableDataInfo;
import com.sxhta.cloud.log.annotation.Log;
import com.sxhta.cloud.log.enums.BusinessType;
import com.sxhta.cloud.remote.domain.SysDictData;
import com.sxhta.cloud.remote.domain.SysUser;
import com.sxhta.cloud.remote.vo.SystemUserCacheVo;
import com.sxhta.cloud.security.annotation.RequiresPermissions;
import com.sxhta.cloud.security.service.TokenService;
import com.sxhta.cloud.system.backend.service.ISysDictDataService;
import com.sxhta.cloud.system.backend.service.ISysDictTypeService;
import jakarta.inject.Inject;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * 数据字典信息
 */
@RestController
@RequestMapping("/dict/data")
public class SysDictDataController extends BaseController {
    @Inject
    private ISysDictDataService dictDataService;

    @Inject
    private ISysDictTypeService dictTypeService;

    @Inject
    private TokenService<SystemUserCacheVo, SysUser> tokenService;

    @RequiresPermissions("system:dict:list")
    @GetMapping("/list")
    public TableDataInfo<SysDictData> list(SysDictData dictData, PageRequest pageRequest) {
        startPage(pageRequest);
        final var list = dictDataService.selectDictDataList(dictData);
        return  CommonResponse.list(list);
    }

    @Log(title = "字典数据", businessType = BusinessType.EXPORT)
    @RequiresPermissions("system:dict:export")
    @PostMapping("/export")
    public void export(HttpServletResponse response, SysDictData dictData) {
        final var list = dictDataService.selectDictDataList(dictData);
        final var util = new ExcelUtil<>(SysDictData.class);
        util.exportExcel(response, list, "字典数据");
    }

    /**
     * 查询字典数据详细
     */
    @RequiresPermissions("system:dict:query")
    @GetMapping(value = "/{dictCode}")
    public CommonResponse<SysDictData> getInfo(@PathVariable Long dictCode) {
        return CommonResponse.success(dictDataService.selectDictDataById(dictCode));
    }

    /**
     * 根据字典类型查询字典数据信息
     */
    @GetMapping(value = "/type/{dictType}")
    public CommonResponse<List<SysDictData>> dictType(@PathVariable String dictType) {
        var data = dictTypeService.selectDictDataByType(dictType);
        if (ObjectUtil.isNull(data)) {
            data = new ArrayList<>();
        }
        return CommonResponse.success(data);
    }

    /**
     * 新增字典类型
     */
    @RequiresPermissions("system:dict:add")
    @Log(title = "字典数据", businessType = BusinessType.INSERT)
    @PostMapping
    public CommonResponse<Boolean> add(@Validated @RequestBody SysDictData dict) {
        dict.setCreateBy(tokenService.getUsername());
        return CommonResponse.result(dictDataService.insertDictData(dict));
    }

    /**
     * 修改保存字典类型
     */
    @RequiresPermissions("system:dict:edit")
    @Log(title = "字典数据", businessType = BusinessType.UPDATE)
    @PutMapping
    public CommonResponse<Boolean> edit(@Validated @RequestBody SysDictData dict) {
        dict.setUpdateBy(tokenService.getUsername());
        return CommonResponse.result(dictDataService.updateDictData(dict));
    }

    /**
     * 删除字典类型
     */
    @RequiresPermissions("system:dict:remove")
    @Log(title = "字典类型", businessType = BusinessType.DELETE)
    @DeleteMapping("/{dictCodes}")
    public CommonResponse<Boolean> remove(@PathVariable Long[] dictCodes) {
        dictDataService.deleteDictDataByIds(dictCodes);
        return CommonResponse.success();
    }
}
