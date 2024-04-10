package com.wheels.cloud.frontend.controller.auth;

import cn.hutool.core.util.ObjectUtil;
import com.sxhta.cloud.common.web.domain.CommonResponse;
import com.sxhta.cloud.security.annotation.InnerAuth;
import com.sxhta.cloud.wheels.remote.request.RemoteRegisterRequest;
import com.sxhta.cloud.wheels.remote.response.wheelsUser.WheelsUserResponse;
import com.sxhta.cloud.wheels.remote.vo.FrontUserCacheVo;
import com.sxhta.cloud.wheels.remote.vo.FrontUserHashVo;
import com.wheels.cloud.frontend.service.user.FrontUserService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/auth/user")
public class FrontUserController implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Inject
    private FrontUserService frontUserService;

    @InnerAuth
    @PostMapping("/register")
    public CommonResponse<Boolean> register(@RequestBody RemoteRegisterRequest request) {
        final var result = frontUserService.register(request);
        System.out.println(result);
        return CommonResponse.success(result);
    }

    /**
     * 获取当前用户信息
     */
    @InnerAuth
    @GetMapping("/info/{account}")
    public CommonResponse<FrontUserCacheVo> getInfoNyAccount(@PathVariable("account") String account) {
        final var userEntity = frontUserService.getUserByAccount(account);
        if (ObjectUtil.isNull(userEntity)) {
            return CommonResponse.error("用户信息为空");
        }
        final var frontUserCacheVo = new FrontUserCacheVo();
        frontUserCacheVo.setUserEntity(userEntity);
        return CommonResponse.success(frontUserCacheVo);
    }

    @InnerAuth
    @GetMapping("/info/id/{id}")
    public CommonResponse<FrontUserHashVo> getHashById(@PathVariable("id") Long id) {
        return CommonResponse.success(frontUserService.getHashById(id));
    }

    @Operation(summary = "用户名模糊查询")
    @InnerAuth
    @GetMapping("/hash/name/list/{userName}")
    public CommonResponse<List<String>> getHashListByUserName(@PathVariable("userName") String userName) {
        return CommonResponse.success(frontUserService.getHashListByUserName(userName));
    }

    @Operation(summary = "电话模糊查询")
    @InnerAuth
    @GetMapping("/hash/phone/list/{userPhone}")
    public CommonResponse<List<String>> getHashListByUserPhone(@PathVariable("userPhone") String userPhone) {
        return CommonResponse.success(frontUserService.getHashListByUserPhone(userPhone));
    }

    @Operation(summary = "HASH查询")
    @InnerAuth
    @GetMapping("/info/hash/{userHash}")
    public CommonResponse<WheelsUserResponse> getInfoByHash(@PathVariable("userHash") String userHash) {
        return CommonResponse.success(frontUserService.getInfoByHash(userHash));
    }

}