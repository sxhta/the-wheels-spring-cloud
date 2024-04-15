package com.sxhta.cloud.wheels.remote.openfeign.user;

import com.sxhta.cloud.common.constant.SecurityConstants;
import com.sxhta.cloud.common.constant.ServiceNameConstants;
import com.sxhta.cloud.common.web.domain.CommonResponse;
import com.sxhta.cloud.wheels.remote.factory.user.FrontUserFallbackFactory;
import com.sxhta.cloud.wheels.remote.request.RemoteRegisterRequest;
import com.sxhta.cloud.wheels.remote.response.wheelsUser.WheelsUserResponse;
import com.sxhta.cloud.wheels.remote.vo.FrontUserCacheVo;
import com.sxhta.cloud.wheels.remote.vo.FrontUserHashVo;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.io.Serializable;
import java.util.List;

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

    //通过id查询HASH
    @GetMapping("/auth/user/info/id/{id}")
    CommonResponse<FrontUserHashVo> getHashById(@PathVariable("id") Long id, @RequestHeader(SecurityConstants.FROM_SOURCE) String source);

    @Operation(summary = "用户名模糊查询")
    @GetMapping("/auth/user/hash/name/list/{userName}")
    CommonResponse<List<String>> getHashListByUserName(@PathVariable("userName") String userName, @RequestHeader(SecurityConstants.FROM_SOURCE) String source);

    @Operation(summary = "电话模糊查询")
    @GetMapping("/auth/user/hash/phone/list/{userPhone}")
    CommonResponse<List<String>> getHashListByUserPhone(@PathVariable("userPhone") String userPhone, @RequestHeader(SecurityConstants.FROM_SOURCE) String source);

    @Operation(summary = "HASH查询")
    @GetMapping("/auth/user/info/hash/{userHash}")
    CommonResponse<WheelsUserResponse> getInfoByHash(@PathVariable("userHash") String userHash, @RequestHeader(SecurityConstants.FROM_SOURCE) String source);


}