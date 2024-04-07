package com.sxhta.cloud.auth.controller;

import cn.hutool.core.util.ObjectUtil;
import com.sxhta.cloud.auth.form.SystemLoginRequest;
import com.sxhta.cloud.auth.form.SystemRegisterRequest;
import com.sxhta.cloud.auth.service.SystemLoginService;
import com.sxhta.cloud.common.component.JwtComponent;
import com.sxhta.cloud.common.exception.CommonException;
import com.sxhta.cloud.common.web.domain.CommonResponse;
import com.sxhta.cloud.remote.domain.SysUser;
import com.sxhta.cloud.remote.vo.SystemUserCacheVo;
import com.sxhta.cloud.security.response.TokenResponse;
import com.sxhta.cloud.security.service.TokenService;
import jakarta.inject.Inject;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


/**
 * token 控制
 */
@RestController
public class SystemTokenController {

    @Inject
    private TokenService<SystemUserCacheVo, SysUser> tokenService;

    @Inject
    private SystemLoginService<SystemUserCacheVo, SysUser> loginService;

    @Inject
    private JwtComponent jwtComponent;

    @PostMapping("login")
    public CommonResponse<TokenResponse> login(@RequestBody SystemLoginRequest form) {
        // 用户登录
        final var userInfo = loginService.login(form.getUsername(), form.getPassword());
        // 获取登录token
        return CommonResponse.success(tokenService.createToken(userInfo));
    }

    @DeleteMapping("logout")
    public CommonResponse<Boolean> logout(HttpServletRequest request) {
        final var token = tokenService.getToken(request);
        if (ObjectUtil.isNotEmpty(token)) {
            final var username = jwtComponent.getUserName(token);
            // 删除用户缓存记录
            final var isDelete = tokenService.deleteLoginUser(token);
            if (!isDelete) {
                throw new CommonException("用户缓存删除失败");
//                throw new ServiceException("当前登录用户已失效，请重新登录", 401);
            }
            // 记录用户退出日志
            loginService.logout(username);
        }
        return CommonResponse.success();
    }

    @PostMapping("refresh")
    public CommonResponse<Boolean> refresh(HttpServletRequest request) {
        final var loginUser = tokenService.getLoginUser(request);
        if (ObjectUtil.isNotNull(loginUser)) {
            // 刷新令牌有效期
            tokenService.refreshToken(loginUser);
            return CommonResponse.success();
        }
        return CommonResponse.success();
    }

    @PostMapping("register")
    public CommonResponse<Boolean> register(@RequestBody SystemRegisterRequest registerBody) {
        // 用户注册
        loginService.register(registerBody.getUsername(), registerBody.getPassword());
        return CommonResponse.success();
    }
}
