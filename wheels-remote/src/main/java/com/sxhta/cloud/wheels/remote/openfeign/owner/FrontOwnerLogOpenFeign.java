package com.sxhta.cloud.wheels.remote.openfeign.owner;

import com.sxhta.cloud.common.constant.SecurityConstants;
import com.sxhta.cloud.common.constant.ServiceNameConstants;
import com.sxhta.cloud.common.web.domain.CommonResponse;
import com.sxhta.cloud.wheels.remote.domain.owner.WheelsFrontOwnerLoginInfo;
import com.sxhta.cloud.wheels.remote.domain.user.WheelsFrontUserOperationLog;
import com.sxhta.cloud.wheels.remote.factory.owner.FrontOwnerLogFallbackFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

/**
 * 日志服务
 */
@FeignClient(contextId = "frontOwnerLogOpenFeign", value = ServiceNameConstants.WHEELS_FRONTEND, fallbackFactory = FrontOwnerLogFallbackFactory.class)
public interface FrontOwnerLogOpenFeign {
    /**
     * 保存系统日志
     *
     * @param operationLog 日志实体
     * @param source     请求来源
     * @return 结果
     */
    @PostMapping("/operation/log")
    CommonResponse<Boolean> saveLog(@RequestBody WheelsFrontUserOperationLog operationLog, @RequestHeader(SecurityConstants.FROM_SOURCE) String source) throws Exception;

    /**
     * 保存访问记录
     *
     * @param loginInfo 访问实体
     * @param source        请求来源
     * @return 结果
     */
    @PostMapping("/login/info")
    CommonResponse<Boolean> saveLoginInfo(@RequestBody WheelsFrontOwnerLoginInfo loginInfo, @RequestHeader(SecurityConstants.FROM_SOURCE) String source);
}
