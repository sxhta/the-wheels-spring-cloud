package com.sxhta.cloud.system.backend.controller;


import com.sxhta.cloud.common.utils.poi.ExcelUtil;
import com.sxhta.cloud.common.web.controller.BaseController;
import com.sxhta.cloud.common.web.domain.CommonResponse;
import com.sxhta.cloud.common.web.page.PageRequest;
import com.sxhta.cloud.common.web.page.TableDataInfo;
import com.sxhta.cloud.log.annotation.Log;
import com.sxhta.cloud.log.enums.BusinessType;
import com.sxhta.cloud.remote.domain.SysOperLog;
import com.sxhta.cloud.security.annotation.InnerAuth;
import com.sxhta.cloud.security.annotation.RequiresPermissions;
import com.sxhta.cloud.system.backend.service.ISysOperLogService;
import jakarta.inject.Inject;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.*;

/**
 * 操作日志记录
 */
@RestController
@RequestMapping("/operlog")
public class SysOperlogController extends BaseController {
    @Inject
    private ISysOperLogService operLogService;

    @RequiresPermissions("system:operlog:list")
    @GetMapping("/list")
    public TableDataInfo<SysOperLog> list(SysOperLog operLog, PageRequest pageRequest) {
        startPage(pageRequest);
        final var list = operLogService.selectOperLogList(operLog);
        return CommonResponse.list(list);
    }

    @Log(title = "操作日志", businessType = BusinessType.EXPORT)
    @RequiresPermissions("system:operlog:export")
    @PostMapping("/export")
    public void export(HttpServletResponse response, SysOperLog operLog) {
        final var list = operLogService.selectOperLogList(operLog);
        final var util = new ExcelUtil<>(SysOperLog.class);
        util.exportExcel(response, list, "操作日志");
    }

    @Log(title = "操作日志", businessType = BusinessType.DELETE)
    @RequiresPermissions("system:operlog:remove")
    @DeleteMapping("/{operIds}")
    public CommonResponse<Boolean> remove(@PathVariable Long[] operIds) {
        return CommonResponse.result(operLogService.deleteOperLogByIds(operIds));
    }

    @RequiresPermissions("system:operlog:remove")
    @Log(title = "操作日志", businessType = BusinessType.CLEAN)
    @DeleteMapping("/clean")
    public CommonResponse<Boolean> clean() {
        operLogService.cleanOperLog();
        return CommonResponse.success();
    }

    @InnerAuth
    @PostMapping
    public CommonResponse<Boolean> add(@RequestBody SysOperLog operLog) {
        return CommonResponse.result(operLogService.insertOperlog(operLog));
    }
}
