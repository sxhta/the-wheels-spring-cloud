package com.sxhta.cloud.system.backend.controller;


import com.sxhta.cloud.cache.redis.service.RedisService;
import com.sxhta.cloud.common.constant.CacheConstants;
import com.sxhta.cloud.common.utils.poi.ExcelUtil;
import com.sxhta.cloud.common.web.controller.BaseController;
import com.sxhta.cloud.common.web.domain.CommonResponse;
import com.sxhta.cloud.common.web.page.PageRequest;
import com.sxhta.cloud.common.web.page.TableDataInfo;
import com.sxhta.cloud.log.annotation.Log;
import com.sxhta.cloud.log.enums.BusinessType;
import com.sxhta.cloud.remote.domain.SysLogininfor;
import com.sxhta.cloud.security.annotation.InnerAuth;
import com.sxhta.cloud.security.annotation.RequiresPermissions;
import com.sxhta.cloud.system.backend.service.ISysLoginInfoService;
import jakarta.inject.Inject;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.*;

/**
 * 系统访问记录
 */
@RestController
@RequestMapping("/logininfor")
public class SysLogininforController extends BaseController {
    @Inject
    private ISysLoginInfoService logininforService;

    @Inject
    private RedisService<String, String> redisService;

    @RequiresPermissions("system:logininfor:list")
    @GetMapping("/list")
    public TableDataInfo<SysLogininfor> list(SysLogininfor logininfor, PageRequest pageRequest) {
        startPage(pageRequest);
        final var list = logininforService.selectLogininforList(logininfor);
        return CommonResponse.list(list);
    }

    @Log(title = "登录日志", businessType = BusinessType.EXPORT)
    @RequiresPermissions("system:logininfor:export")
    @PostMapping("/export")
    public void export(HttpServletResponse response, SysLogininfor logininfor) {
        final var list = logininforService.selectLogininforList(logininfor);
        final var util = new ExcelUtil<>(SysLogininfor.class);
        util.exportExcel(response, list, "登录日志");
    }

    @RequiresPermissions("system:logininfor:remove")
    @Log(title = "登录日志", businessType = BusinessType.DELETE)
    @DeleteMapping("/{infoIds}")
    public CommonResponse<Boolean> remove(@PathVariable Long[] infoIds) {
        return CommonResponse.result(logininforService.deleteLogininforByIds(infoIds));
    }

    @RequiresPermissions("system:logininfor:remove")
    @Log(title = "登录日志", businessType = BusinessType.DELETE)
    @DeleteMapping("/clean")
    public CommonResponse<Boolean> clean() {
        logininforService.cleanLogininfor();
        return CommonResponse.success();
    }

    @RequiresPermissions("system:logininfor:unlock")
    @Log(title = "账户解锁", businessType = BusinessType.OTHER)
    @GetMapping("/unlock/{userName}")
    public CommonResponse<Boolean> unlock(@PathVariable("userName") String userName) {
        redisService.deleteObject(CacheConstants.PWD_ERR_CNT_KEY + userName);
        return CommonResponse.success();
    }

    @InnerAuth
    @PostMapping
    public CommonResponse<Boolean> add(@RequestBody SysLogininfor logininfor) {
        return CommonResponse.result(logininforService.insertLogininfor(logininfor));
    }
}
