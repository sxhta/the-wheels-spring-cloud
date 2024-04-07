package com.sxhta.cloud.system.backend.controller;


import com.sxhta.cloud.common.web.controller.BaseController;
import com.sxhta.cloud.common.web.domain.CommonResponse;
import com.sxhta.cloud.common.web.page.PageRequest;
import com.sxhta.cloud.common.web.page.TableDataInfo;
import com.sxhta.cloud.log.annotation.Log;
import com.sxhta.cloud.log.enums.BusinessType;
import com.sxhta.cloud.remote.domain.SysUser;
import com.sxhta.cloud.remote.vo.SystemUserCacheVo;
import com.sxhta.cloud.security.annotation.RequiresPermissions;
import com.sxhta.cloud.security.service.TokenService;
import com.sxhta.cloud.system.backend.domain.SysNotice;
import com.sxhta.cloud.system.backend.service.ISysNoticeService;
import jakarta.inject.Inject;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * 公告 信息操作处理
 */
@RestController
@RequestMapping("/notice")
public class SysNoticeController extends BaseController {
    @Inject
    private ISysNoticeService noticeService;

    @Inject
    private TokenService<SystemUserCacheVo, SysUser> tokenService;

    /**
     * 获取通知公告列表
     */
    @RequiresPermissions("system:notice:list")
    @GetMapping("/list")
    public TableDataInfo<SysNotice> list(SysNotice notice, PageRequest pageRequest) {
        startPage(pageRequest);
        final var list = noticeService.selectNoticeList(notice);
        return CommonResponse.list(list);
    }

    /**
     * 根据通知公告编号获取详细信息
     */
    @RequiresPermissions("system:notice:query")
    @GetMapping(value = "/{noticeId}")
    public CommonResponse<SysNotice> getInfo(@PathVariable Long noticeId) {
        return CommonResponse.success(noticeService.selectNoticeById(noticeId));
    }

    /**
     * 新增通知公告
     */
    @RequiresPermissions("system:notice:add")
    @Log(title = "通知公告", businessType = BusinessType.INSERT)
    @PostMapping
    public CommonResponse<Boolean> add(@Validated @RequestBody SysNotice notice) {
        notice.setCreateBy(tokenService.getUsername());
        return CommonResponse.result(noticeService.insertNotice(notice));
    }

    /**
     * 修改通知公告
     */
    @RequiresPermissions("system:notice:edit")
    @Log(title = "通知公告", businessType = BusinessType.UPDATE)
    @PutMapping
    public CommonResponse<Boolean> edit(@Validated @RequestBody SysNotice notice) {
        notice.setUpdateBy(tokenService.getUsername());
        return CommonResponse.result(noticeService.updateNotice(notice));
    }

    /**
     * 删除通知公告
     */
    @RequiresPermissions("system:notice:remove")
    @Log(title = "通知公告", businessType = BusinessType.DELETE)
    @DeleteMapping("/{noticeIds}")
    public CommonResponse<Boolean> remove(@PathVariable Long[] noticeIds) {
        return CommonResponse.result(noticeService.deleteNoticeByIds(noticeIds));
    }
}
