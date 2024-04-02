package com.sxhta.cloud.security.interceptor;


import cn.hutool.core.util.ObjectUtil;
import com.sxhta.cloud.common.component.ServletComponent;
import com.sxhta.cloud.common.constant.SecurityConstants;
import com.sxhta.cloud.common.context.SecurityContextHolder;
import com.sxhta.cloud.security.service.AuthService;
import com.sxhta.cloud.security.service.TokenService;
import jakarta.annotation.Nonnull;
import jakarta.inject.Inject;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.AsyncHandlerInterceptor;

import java.io.Serial;
import java.io.Serializable;

/**
 * 自定义请求头拦截器，将Header数据封装到线程变量中方便获取
 * 注意：此拦截器会同时验证当前用户有效期自动刷新有效期
 */
@Component
public class HeaderInterceptor implements AsyncHandlerInterceptor, Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Inject
    private AuthService authService;

    @Inject
    private TokenService tokenService;

    @Inject
    private SecurityContextHolder securityContextHolder;

    @Inject
    private ServletComponent servletComponent;

    @Override
    public boolean preHandle(@Nonnull HttpServletRequest request, @Nonnull HttpServletResponse response, @Nonnull Object handler) {
        if (!(handler instanceof HandlerMethod)) {
            return true;
        }

        securityContextHolder.setUserId(servletComponent.getHeader(request, SecurityConstants.DETAILS_USER_ID));
        securityContextHolder.setUserName(servletComponent.getHeader(request, SecurityConstants.DETAILS_USERNAME));
        securityContextHolder.setUserKey(servletComponent.getHeader(request, SecurityConstants.USER_KEY));

        final var token = tokenService.getToken();
        if (ObjectUtil.isNotEmpty(token)) {
            final var loginUser = tokenService.getLoginUser(token);
            if (ObjectUtil.isNotNull(loginUser)) {
                tokenService.verifyAndRefreshToken(loginUser);
                securityContextHolder.set(SecurityConstants.LOGIN_USER, loginUser);
            }
        }
        return true;
    }

    @Override
    public void afterCompletion(@Nonnull HttpServletRequest request, @Nonnull HttpServletResponse response, @Nonnull Object handler, Exception ex) {
        securityContextHolder.remove();
    }
}
