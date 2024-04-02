package com.sxhta.cloud.security.aspect;

import com.sxhta.cloud.security.annotation.RequiresLogin;
import com.sxhta.cloud.security.annotation.RequiresPermissions;
import com.sxhta.cloud.security.annotation.RequiresRoles;
import com.sxhta.cloud.security.service.AuthService;
import com.sxhta.cloud.security.service.TokenService;
import jakarta.inject.Inject;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.io.Serial;
import java.io.Serializable;
import java.lang.reflect.Method;

/**
 * 基于 Spring Aop 的注解鉴权
 *
 * @author kong
 */
@Aspect
@Component
public class PreAuthorizeAspect implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Inject
    private AuthService authService;

    @Inject
    private TokenService tokenService;

    /**
     * 构建
     */
    public PreAuthorizeAspect() {
    }

    /**
     * 定义AOP签名 (切入所有使用鉴权注解的方法)
     * TODO::这个方式非常不优雅
     */
    public static final String POINTCUT_SIGN = " @annotation(com.sxhta.cloud.security.annotation.RequiresLogin) || "
            + "@annotation(com.sxhta.cloud.security.annotation.RequiresPermissions) || "
            + "@annotation(com.sxhta.cloud.security.annotation.RequiresRoles)";

    /**
     * 声明AOP签名
     */
    @Pointcut(POINTCUT_SIGN)
    public void pointcut() {
    }

    /**
     * 环绕切入
     *
     * @param joinPoint 切面对象
     * @return 底层方法执行后的返回值
     * @throws Throwable 底层方法抛出的异常
     */
    @Around("pointcut()")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        // 注解鉴权
        final var signature = (MethodSignature) joinPoint.getSignature();
        checkMethodAnnotation(signature.getMethod());
        try {
            // 执行原有逻辑
            return joinPoint.proceed();
        } catch (Throwable e) {
            throw e;
        }
    }

    /**
     * 对一个Method对象进行注解检查
     */
    public void checkMethodAnnotation(Method method) {
        // 校验 @RequiresLogin 注解
        final var requiresLogin = method.getAnnotation(RequiresLogin.class);
        if (requiresLogin != null) {
            tokenService.checkLogin();
        }

        // 校验 @RequiresRoles 注解
        final var requiresRoles = method.getAnnotation(RequiresRoles.class);
        if (requiresRoles != null) {
            authService.checkRole(requiresRoles);
        }

        // 校验 @RequiresPermissions 注解
        final var requiresPermissions = method.getAnnotation(RequiresPermissions.class);
        if (requiresPermissions != null) {
            authService.checkPermission(requiresPermissions);
        }
    }
}
