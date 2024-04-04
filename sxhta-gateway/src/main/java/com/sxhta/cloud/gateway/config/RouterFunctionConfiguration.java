package com.sxhta.cloud.gateway.config;

import com.sxhta.cloud.gateway.handler.ValidateCodeHandler;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import java.io.Serial;
import java.io.Serializable;

/**
 * 路由配置信息
 */
@Singleton
@Configuration
public class RouterFunctionConfiguration implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Inject
    private ValidateCodeHandler validateCodeHandler;

    @Bean
    public RouterFunction<? extends ServerResponse> routerFunction() {
        return RouterFunctions.route(
                RequestPredicates.GET("/code").and(RequestPredicates.accept(MediaType.TEXT_PLAIN)),
                validateCodeHandler);
    }
}
