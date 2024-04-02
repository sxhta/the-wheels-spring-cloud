package com.sxhta.cloud.security.config;

import com.sxhta.cloud.security.interceptor.HeaderInterceptor;
import jakarta.inject.Inject;
import lombok.Getter;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.io.Serial;
import java.io.Serializable;

/**
 * 拦截器配置
 */
@Getter
@Configuration
public class WebMvcConfiguration implements WebMvcConfigurer , Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * -- GETTER --
     *  自定义请求头拦截器
     */
    @Inject
    private HeaderInterceptor headerInterceptor;

    /**
     * 不需要拦截地址
     */
    public static final String[] excludeUrls = {"/login", "/logout", "/refresh"};

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(getHeaderInterceptor())
                .addPathPatterns("/**")
                .excludePathPatterns(excludeUrls)
                .order(-10);
    }

}
