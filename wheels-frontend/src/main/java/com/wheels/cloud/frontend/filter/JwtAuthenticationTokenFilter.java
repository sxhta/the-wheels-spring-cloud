package com.wheels.cloud.frontend.filter;

import cn.hutool.core.util.ObjectUtil;
import com.wheels.cloud.frontend.service.auth.FrontTokenService;
import jakarta.annotation.Nonnull;
import jakarta.inject.Inject;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/**
 * token过滤器 验证token有效性
 */
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {

    @Inject
    private FrontTokenService frontTokenService;

    @Override
    protected void doFilterInternal(@Nonnull HttpServletRequest request, @Nonnull HttpServletResponse response, @Nonnull FilterChain filterChain) throws ServletException, IOException {
        final var userDetailsVo = frontTokenService.getUserDetailsVoByHttpServletRequest(request);
//        if (ObjectUtil.isNotNull(loginUser) && StringUtils.isNull(SecurityUtils.getAuthentication())) {
        if (ObjectUtil.isNotNull(userDetailsVo)) {
//            tokenComponent.verifyToken(loginUser);
            final var authenticationToken = new UsernamePasswordAuthenticationToken(userDetailsVo, null, userDetailsVo.getAuthorities());
            authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            // 将authentication信息放入到上下文对象中
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        }
        filterChain.doFilter(request, response);
    }
}
