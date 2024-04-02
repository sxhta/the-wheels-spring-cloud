package com.wheels.cloud.frontend.controller.auth;

import com.sxhta.cloud.common.web.domain.CommonResponse;
import com.wheels.cloud.frontend.request.auth.LoginRequest;
import com.wheels.cloud.frontend.request.auth.RegisterRequest;
import com.wheels.cloud.frontend.service.auth.FrontAuthService;
import jakarta.inject.Inject;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/auth")
public class AuthController {

    @Inject
    private FrontAuthService frontAuthService;

    @PostMapping("/register")
    public CommonResponse register(@RequestBody final RegisterRequest request) {
        final var result = frontAuthService.register(request);
        return CommonResponse.success(result);
    }

    @PostMapping("/login")
    public CommonResponse login(@RequestBody final LoginRequest request) {
        final var result = frontAuthService.login(request);
        return CommonResponse.success(result);
    }

    @PostMapping("/logout")
    public CommonResponse logout(final HttpServletRequest request) {
        final var result = frontAuthService.logout(request);
        return CommonResponse.success(result);
    }

    @GetMapping("/test")
    public CommonResponse test() {
        return CommonResponse.success("测试授权");
    }
}
