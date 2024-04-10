package com.sxhta.cloud.wheels.config;

import com.sxhta.cloud.remote.filter.ResponseFilter;
import jakarta.annotation.Nonnull;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import jakarta.servlet.Filter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.io.Serial;
import java.io.Serializable;

/**
 * token验证拦截器
 */
@Singleton
@Configuration
public class WebConfiguration implements WebMvcConfigurer, Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Inject
    private ResponseFilter responseFilter;

    @Override
    public void addInterceptors(@Nonnull InterceptorRegistry registry) {
    }

    @Override
    public void addResourceHandlers(@Nonnull ResourceHandlerRegistry registry) {
    }

    @Bean
    public FilterRegistrationBean<Filter> filterRegister() {
        //注册过滤器
        return new FilterRegistrationBean<>(responseFilter);
    }
}
