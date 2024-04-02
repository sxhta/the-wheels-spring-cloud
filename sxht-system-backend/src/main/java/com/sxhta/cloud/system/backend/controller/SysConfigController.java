package com.sxhta.cloud.system.backend.controller;


import com.sxhta.cloud.common.utils.poi.ExcelUtil;
import com.sxhta.cloud.common.web.controller.BaseController;
import com.sxhta.cloud.common.web.domain.CommonResponse;
import com.sxhta.cloud.common.web.page.PageRequest;
import com.sxhta.cloud.common.web.page.TableDataInfo;
import com.sxhta.cloud.log.annotation.Log;
import com.sxhta.cloud.log.enums.BusinessType;
import com.sxhta.cloud.remote.domain.SysUser;
import com.sxhta.cloud.remote.model.SystemUserCacheVo;
import com.sxhta.cloud.security.annotation.RequiresPermissions;
import com.sxhta.cloud.security.service.TokenService;
import com.sxhta.cloud.system.backend.domain.SysConfig;
import com.sxhta.cloud.system.backend.service.SysConfigService;
import jakarta.inject.Inject;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * 参数配置 信息操作处理
 */
@RestController
@RequestMapping("/config")
public class SysConfigController extends BaseController {
    @Inject
    private SysConfigService sysConfigService;

    @Inject
    private TokenService<SystemUserCacheVo, SysUser> tokenService;

    /**
     * 获取参数配置列表
     */
    @RequiresPermissions("system:config:list")
    @GetMapping("/list")
    public TableDataInfo<SysConfig> list(SysConfig config, PageRequest pageRequest) {
        startPage(pageRequest);
        final var list = sysConfigService.selectConfigList(config);
        return CommonResponse.list(list);
    }

    @Log(title = "参数管理", businessType = BusinessType.EXPORT)
    @RequiresPermissions("system:config:export")
    @PostMapping("/export")
    public void export(HttpServletResponse response, SysConfig config) {
        final var list = sysConfigService.selectConfigList(config);
        final var util = new ExcelUtil<>(SysConfig.class);
        util.exportExcel(response, list, "参数数据");
    }

    /**
     * 根据参数编号获取详细信息
     */
    @GetMapping(value = "/{configId}")
    public CommonResponse<SysConfig> getInfo(@PathVariable Long configId) {
        return CommonResponse.success(sysConfigService.selectConfigById(configId));
    }

    /**
     * 根据参数键名查询参数值
     */
    @GetMapping(value = "/configKey/{configKey}")
    public CommonResponse<String> getConfigKey(@PathVariable String configKey) {
        return CommonResponse.success(sysConfigService.selectConfigByKey(configKey));
    }

    /**
     * 新增参数配置
     */
    @RequiresPermissions("system:config:add")
    @Log(title = "参数管理", businessType = BusinessType.INSERT)
    @PostMapping
    public CommonResponse<Boolean> add(@Validated @RequestBody SysConfig config) {
        if (!sysConfigService.checkConfigKeyUnique(config)) {
            return CommonResponse.error("新增参数'" + config.getConfigName() + "'失败，参数键名已存在");
        }
        config.setCreateBy(tokenService.getUsername());
        return CommonResponse.result(sysConfigService.insertConfig(config));
    }

    /**
     * 修改参数配置
     */
    @RequiresPermissions("system:config:edit")
    @Log(title = "参数管理", businessType = BusinessType.UPDATE)
    @PutMapping
    public CommonResponse<Boolean> edit(@Validated @RequestBody SysConfig config) {
        if (!sysConfigService.checkConfigKeyUnique(config)) {
            return CommonResponse.error("修改参数'" + config.getConfigName() + "'失败，参数键名已存在");
        }
        config.setUpdateBy(tokenService.getUsername());
        return CommonResponse.result(sysConfigService.updateConfig(config));
    }

    /**
     * 删除参数配置
     */
    @RequiresPermissions("system:config:remove")
    @Log(title = "参数管理", businessType = BusinessType.DELETE)
    @DeleteMapping("/{configIds}")
    public CommonResponse<Boolean> remove(@PathVariable Long[] configIds) {
        sysConfigService.deleteConfigByIds(configIds);
        return CommonResponse.success();
    }

    /**
     * 刷新参数缓存
     */
    @RequiresPermissions("system:config:remove")
    @Log(title = "参数管理", businessType = BusinessType.CLEAN)
    @DeleteMapping("/refreshCache")
    public CommonResponse<Boolean> refreshCache() {
        sysConfigService.resetConfigCache();
        return CommonResponse.success();
    }
}
