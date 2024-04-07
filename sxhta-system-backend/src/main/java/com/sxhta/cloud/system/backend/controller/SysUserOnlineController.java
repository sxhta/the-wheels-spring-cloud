package com.sxhta.cloud.system.backend.controller;


import cn.hutool.core.util.ObjectUtil;
import com.sxhta.cloud.cache.redis.service.RedisService;
import com.sxhta.cloud.common.constant.CacheConstants;
import com.sxhta.cloud.common.web.controller.BaseController;
import com.sxhta.cloud.common.web.domain.CommonResponse;
import com.sxhta.cloud.common.web.page.TableDataInfo;
import com.sxhta.cloud.log.annotation.Log;
import com.sxhta.cloud.log.enums.BusinessType;
import com.sxhta.cloud.remote.vo.SystemUserCacheVo;
import com.sxhta.cloud.security.annotation.RequiresPermissions;
import com.sxhta.cloud.system.backend.domain.SysUserOnline;
import com.sxhta.cloud.system.backend.service.ISysUserOnlineService;
import jakarta.inject.Inject;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collections;

/**
 * 在线用户监控
 */
@RestController
@RequestMapping("/online")
public class SysUserOnlineController extends BaseController {
    @Inject
    private ISysUserOnlineService userOnlineService;

    @Inject
    private RedisService<String, SystemUserCacheVo> redisService;

    @RequiresPermissions("monitor:online:list")
    @GetMapping("/list")
    public TableDataInfo<SysUserOnline> list(String ipaddr, String userName) {
        final var keys = redisService.keys(CacheConstants.LOGIN_TOKEN_KEY + "*");
        final var userOnlineList = new ArrayList<SysUserOnline>();
        for (final var key : keys) {
            final SystemUserCacheVo user = redisService.getCacheObject(key);
            if (ObjectUtil.isNotEmpty(ipaddr) && ObjectUtil.isNotEmpty(userName)) {
                userOnlineList.add(userOnlineService.selectOnlineByInfo(ipaddr, userName, user));
            } else if (ObjectUtil.isNotEmpty(ipaddr)) {
                userOnlineList.add(userOnlineService.selectOnlineByIpaddr(ipaddr, user));
            } else if (ObjectUtil.isNotEmpty(userName)) {
                userOnlineList.add(userOnlineService.selectOnlineByUserName(userName, user));
            } else {
                userOnlineList.add(userOnlineService.loginUserToUserOnline(user));
            }
        }
        Collections.reverse(userOnlineList);
        userOnlineList.removeAll(Collections.singleton(null));
        return CommonResponse.list(userOnlineList);
    }

    /**
     * 强退用户
     */
    @RequiresPermissions("monitor:online:forceLogout")
    @Log(title = "在线用户", businessType = BusinessType.FORCE)
    @DeleteMapping("/{tokenId}")
    public CommonResponse<Boolean> forceLogout(@PathVariable String tokenId) {
        redisService.deleteObject(CacheConstants.LOGIN_TOKEN_KEY + tokenId);
        return CommonResponse.success();
    }
}
