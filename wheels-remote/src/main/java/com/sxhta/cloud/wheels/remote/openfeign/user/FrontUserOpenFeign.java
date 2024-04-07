package com.sxhta.cloud.wheels.remote.openfeign.user;

import com.sxhta.cloud.common.constant.SecurityConstants;
import com.sxhta.cloud.common.constant.ServiceNameConstants;
import com.sxhta.cloud.common.web.domain.CommonResponse;
import com.sxhta.cloud.wheels.remote.vo.FrontUserCacheVo;
import com.sxhta.cloud.wheels.remote.domain.user.WheelsFrontUser;
import com.sxhta.cloud.wheels.remote.factory.user.FrontUserFallbackFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

/**
 * 用户服务
 */
@FeignClient(contextId = "frontUserOpenFeign", value = ServiceNameConstants.WHEELS_FRONTEND, fallbackFactory = FrontUserFallbackFactory.class)
public interface FrontUserOpenFeign {
    /**
     * 通过用户名查询用户信息
     *
     * @param username 用户名
     * @param source   请求来源
     * @return 结果
     */
    @GetMapping("/user/info/{username}")
    CommonResponse<FrontUserCacheVo> getUserInfo(@PathVariable("username") String username, @RequestHeader(SecurityConstants.FROM_SOURCE) String source);

    /**
     * 注册用户信息
     *
     * @param sysUser 用户信息
     * @param source  请求来源
     * @return 结果
     */
    @PostMapping("/user/register")
    CommonResponse<FrontUserCacheVo> registerUserInfo(@RequestBody WheelsFrontUser sysUser, @RequestHeader(SecurityConstants.FROM_SOURCE) String source);
}
