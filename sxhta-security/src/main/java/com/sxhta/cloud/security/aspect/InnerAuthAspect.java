package com.sxhta.cloud.security.aspect;

import com.sxhta.cloud.common.component.ServletComponent;
import com.sxhta.cloud.common.constant.SecurityConstants;
import com.sxhta.cloud.common.exception.InnerAuthException;
import com.sxhta.cloud.security.annotation.InnerAuth;
import jakarta.inject.Inject;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;

import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;

/**
 * 内部服务调用验证处理
 */
@Aspect
@Component
public class InnerAuthAspect implements Ordered, Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Inject
    private ServletComponent servletComponent;

    @Around("@annotation(innerAuth)")
    public Object innerAround(ProceedingJoinPoint point, InnerAuth innerAuth) throws Throwable {
        final var source = Objects.requireNonNull(servletComponent.getRequest()).getHeader(SecurityConstants.FROM_SOURCE);
        // 内部请求验证
        if (!StringUtils.equals(SecurityConstants.INNER, source)) {
            throw new InnerAuthException("没有内部访问权限，不允许访问");
        }

        final var userid = servletComponent.getRequest().getHeader(SecurityConstants.DETAILS_USER_ID);
        final var username = servletComponent.getRequest().getHeader(SecurityConstants.DETAILS_USERNAME);
        // 用户信息验证
        if (innerAuth.isUser() && (StringUtils.isEmpty(userid) || StringUtils.isEmpty(username))) {
            throw new InnerAuthException("没有设置用户信息，不允许访问 ");
        }
        return point.proceed();
    }

    /**
     * 确保在权限认证aop执行前执行
     */
    @Override
    public int getOrder() {
        return Ordered.HIGHEST_PRECEDENCE + 1;
    }
}
