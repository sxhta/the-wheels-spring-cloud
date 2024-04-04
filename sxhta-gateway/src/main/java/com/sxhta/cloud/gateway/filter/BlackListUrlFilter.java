package com.sxhta.cloud.gateway.filter;

import com.sxhta.cloud.common.component.ServletComponent;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.stereotype.Component;

import java.io.Serial;
import java.io.Serializable;

/**
 * 黑名单过滤器
 */
@Singleton
@Component
public final class BlackListUrlFilter extends AbstractGatewayFilterFactory<BlackListUrlFilterConfig> implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Inject
    private ServletComponent servletComponent;

    @Override
    public GatewayFilter apply(BlackListUrlFilterConfig config) {
        return (exchange, chain) -> {

            final var url = exchange.getRequest().getURI().getPath();
            if (config.matchBlacklist(url)) {
                return servletComponent.webFluxResponseWriter(exchange.getResponse(), "请求地址不允许访问");
            }

            return chain.filter(exchange);
        };
    }

    public BlackListUrlFilter() {
        super(BlackListUrlFilterConfig.class);
    }

}
