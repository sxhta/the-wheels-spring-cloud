package com.sxhta.cloud.gateway.config;

import com.sxhta.cloud.gateway.handler.SentinelFallbackHandler;
import jakarta.inject.Inject;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;

import java.io.Serial;
import java.io.Serializable;

/**
 * 网关限流配置
 */
@Configuration
public class GatewayConfig implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Inject
    private SentinelFallbackHandler sentinelFallbackHandler;

    @Bean
    @Order(Ordered.HIGHEST_PRECEDENCE)
    public SentinelFallbackHandler sentinelGatewayExceptionHandler() {
        return sentinelFallbackHandler;
    }
}