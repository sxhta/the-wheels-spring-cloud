package com.sxhta.cloud.wheels.remote.factory.user;

import com.sxhta.cloud.common.web.domain.CommonResponse;
import com.sxhta.cloud.remote.domain.SysLogininfor;
import com.sxhta.cloud.remote.domain.SysOperLog;
import com.sxhta.cloud.wheels.remote.openfeign.user.FrontUserLogOpenFeign;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;

/**
 * 日志服务降级处理
 */
@Component
public class FrontUserLogFallbackFactory implements FallbackFactory<FrontUserLogOpenFeign> {
    private final Logger logger = LoggerFactory.getLogger(FrontUserLogFallbackFactory.class);

    @Override
    public FrontUserLogOpenFeign create(Throwable throwable) {
        logger.error("日志服务调用失败:{}", throwable.getMessage());
        return new FrontUserLogOpenFeign() {
            @Override
            public CommonResponse<Boolean> saveLog(SysOperLog sysOperLog, String source) {
                return CommonResponse.error("保存操作日志失败:" + throwable.getMessage());
            }

            @Override
            public CommonResponse<Boolean> saveLogininfor(SysLogininfor sysLogininfor, String source) {
                return CommonResponse.error("保存登录日志失败:" + throwable.getMessage());
            }
        };

    }
}
