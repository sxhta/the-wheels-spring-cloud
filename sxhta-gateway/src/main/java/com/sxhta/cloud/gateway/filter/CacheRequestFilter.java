package com.sxhta.cloud.gateway.filter;

import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.OrderedGatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.cloud.gateway.support.ServerWebExchangeUtils;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.io.Serial;
import java.io.Serializable;
import java.util.Collections;
import java.util.List;

/**
 * 获取body请求数据（解决流不能重复读取问题）
 */
@Singleton
@Component
public final class CacheRequestFilter extends AbstractGatewayFilterFactory<CacheRequestFilterConfig> implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Inject
    private CacheRequestGatewayFilter cacheRequestGatewayFilter;

    public CacheRequestFilter() {
        super(CacheRequestFilterConfig.class);
    }

    @Override
    public String name() {
        return CacheRequestFilter.class.getSimpleName();
    }

    @Override
    public GatewayFilter apply(CacheRequestFilterConfig config) {
        final var order = config.getOrder();
        if (order == null) {
            return cacheRequestGatewayFilter;
        }
        return new OrderedGatewayFilter(cacheRequestGatewayFilter, order);
    }


    @Override
    public List<String> shortcutFieldOrder() {
        return Collections.singletonList("order");
    }
}