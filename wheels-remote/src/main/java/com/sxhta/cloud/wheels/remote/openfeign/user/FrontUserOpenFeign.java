package com.sxhta.cloud.wheels.remote.openfeign.user;

import com.sxhta.cloud.common.constant.SecurityConstants;
import com.sxhta.cloud.common.constant.ServiceNameConstants;
import com.sxhta.cloud.common.web.domain.CommonResponse;
import com.sxhta.cloud.wheels.remote.factory.user.FrontUserFallbackFactory;
import com.sxhta.cloud.wheels.remote.request.RemoteRegisterRequest;
import com.sxhta.cloud.wheels.remote.vo.FrontUserCacheVo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.io.Serializable;

/**
 * 用户服务
 */
@FeignClient(contextId = "frontUserOpenFeign", value = ServiceNameConstants.WHEELS_FRONTEND, fallbackFactory = FrontUserFallbackFactory.class)
public interface FrontUserOpenFeign extends Serializable {

    /**
     * 通过用户名查询用户信息
     *
     * @param account 用户名
     * @param source  请求来源
     * @return 结果
     */
    @GetMapping("/auth/user/info/{account}")
    CommonResponse<FrontUserCacheVo> getUserInfo(@PathVariable("account") String account, @RequestHeader(SecurityConstants.FROM_SOURCE) String source);

    /**
     * 注册用户信息
     *
     * @param request 用户信息
     * @param source  请求来源
     * @return 结果
     */
    @PostMapping("/auth/user/register")
    CommonResponse<Boolean> register(@RequestBody RemoteRegisterRequest request, @RequestHeader(SecurityConstants.FROM_SOURCE) String source);
}