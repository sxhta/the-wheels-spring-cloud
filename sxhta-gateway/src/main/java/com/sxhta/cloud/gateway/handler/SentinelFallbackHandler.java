package com.sxhta.cloud.gateway.handler;

import com.alibaba.csp.sentinel.adapter.gateway.sc.callback.GatewayCallbackManager;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.sxhta.cloud.common.component.ServletComponent;
import jakarta.annotation.Nonnull;
import jakarta.inject.Inject;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerResponse;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebExceptionHandler;
import reactor.core.publisher.Mono;

import java.io.Serial;
import java.io.Serializable;

/**
 * 自定义限流异常处理
 */
@Component
public class SentinelFallbackHandler implements WebExceptionHandler, Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Inject
    private ServletComponent servletComponent;

    private Mono<Void> writeResponse(ServerWebExchange exchange) {
        return servletComponent.webFluxResponseWriter(exchange.getResponse(), "请求超过最大数，请稍候再试");
    }

    @Nonnull
    @Override
    public Mono<Void> handle(ServerWebExchange exchange, @Nonnull Throwable ex) {
        if (exchange.getResponse().isCommitted()) {
            return Mono.error(ex);
        }
        if (!BlockException.isBlockException(ex)) {
            return Mono.error(ex);
        }
        return handleBlockedRequest(exchange, ex).flatMap(response -> writeResponse(exchange));
    }

    private Mono<ServerResponse> handleBlockedRequest(ServerWebExchange exchange, Throwable throwable) {
        return GatewayCallbackManager.getBlockHandler().handleRequest(exchange, throwable);
    }
}
