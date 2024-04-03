package com.sxhta.cloud.wheels.auth.controller.owner;

import cn.hutool.core.util.ObjectUtil;
import com.sxhta.cloud.common.component.JwtComponent;
import com.sxhta.cloud.common.exception.CommonException;
import com.sxhta.cloud.common.web.domain.CommonResponse;
import com.sxhta.cloud.security.response.TokenResponse;
import com.sxhta.cloud.security.service.TokenService;
import com.sxhta.cloud.wheels.auth.form.user.FrontUserLoginRequest;
import com.sxhta.cloud.wheels.auth.form.user.FrontUserRegisterRequest;
import com.sxhta.cloud.wheels.auth.service.user.FrontUserLoginService;
import com.sxhta.cloud.wheels.remote.domain.owner.WheelsFrontOwner;
import com.sxhta.cloud.wheels.remote.vo.FrontOwnerCacheVo;
import jakarta.inject.Inject;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.*;


/**
 * token 控制
 */
@RestController
@RequestMapping(value = "/owner")
public class FrontOwnerTokenController {

    @Inject
    private TokenService<FrontOwnerCacheVo, WheelsFrontOwner> frontUserTokenService;

    @Inject
    private FrontUserLoginService<FrontOwnerCacheVo, WheelsFrontOwner> frontUserLoginService;

    @Inject
    private JwtComponent jwtComponent;

    @PostMapping("login")
    public CommonResponse<TokenResponse> login(@RequestBody FrontUserLoginRequest form) {
        // 用户登录
        final var userInfo = frontUserLoginService.login(form.getUsername(), form.getPassword());
        // 获取登录token
        return CommonResponse.success(frontUserTokenService.createToken(userInfo));
    }

    @DeleteMapping("logout")
    public CommonResponse<Boolean> logout(HttpServletRequest request) {
        final var token = frontUserTokenService.getToken(request);
        if (ObjectUtil.isNotEmpty(token)) {
            final var username = jwtComponent.getUserName(token);
            // 删除用户缓存记录
            final var isDelete = frontUserTokenService.deleteLoginUser(token);
            if (!isDelete) {
                throw new CommonException("用户缓存删除失败");
            }
            // 记录用户退出日志
            frontUserLoginService.logout(username);
        }
        return CommonResponse.success();
    }

    @PostMapping("refresh")
    public CommonResponse<Boolean> refresh(HttpServletRequest request) {
        final var loginUser = frontUserTokenService.getLoginUser(request);
        if (ObjectUtil.isNotNull(loginUser)) {
            // 刷新令牌有效期
            frontUserTokenService.refreshToken(loginUser);
            return CommonResponse.success();
        }
        return CommonResponse.success();
    }

    @PostMapping("register")
    public CommonResponse<Boolean> register(@RequestBody FrontUserRegisterRequest registerBody) {
        // 用户注册
        frontUserLoginService.register(registerBody.getUsername(), registerBody.getPassword());
        return CommonResponse.success();
    }
}
