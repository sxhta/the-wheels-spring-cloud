package com.sxhta.cloud.security.feign;

import feign.RequestInterceptor;
import jakarta.inject.Inject;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.Serial;
import java.io.Serializable;

/**
 * Feign 配置注册
 **/
@Configuration
public class FeignAutoConfiguration implements Serializable {

    @Inject
    private FeignRequestInterceptor feignRequestInterceptor;

    @Serial
    private static final long serialVersionUID = 1L;

    @Bean
    public RequestInterceptor requestInterceptor() {
        return feignRequestInterceptor;
    }
}
