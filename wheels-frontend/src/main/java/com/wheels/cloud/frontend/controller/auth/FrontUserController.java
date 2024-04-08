package com.wheels.cloud.frontend.controller.auth;

import cn.hutool.core.util.ObjectUtil;
import com.sxhta.cloud.common.web.domain.CommonResponse;
import com.sxhta.cloud.security.annotation.InnerAuth;
import com.sxhta.cloud.wheels.remote.request.RemoteRegisterRequest;
import com.sxhta.cloud.wheels.remote.vo.FrontUserCacheVo;
import com.wheels.cloud.frontend.service.user.FrontUserService;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.io.Serial;
import java.io.Serializable;

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
}