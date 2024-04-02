package com.sxhta.cloud.log.service;

import com.sxhta.cloud.common.constant.SecurityConstants;
import com.sxhta.cloud.remote.RemoteLogOpenFeign;
import com.sxhta.cloud.remote.domain.SysOperLog;
import jakarta.inject.Inject;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

/**
 * 异步调用日志服务
 */
@Service
public class AsyncLogService {

    @Inject
    private RemoteLogOpenFeign remoteLogService;

    /**
     * 保存系统日志记录
     */
    @Async
    public void saveSysLog(SysOperLog sysOperLog) throws Exception {
        remoteLogService.saveLog(sysOperLog, SecurityConstants.INNER);
    }
}
