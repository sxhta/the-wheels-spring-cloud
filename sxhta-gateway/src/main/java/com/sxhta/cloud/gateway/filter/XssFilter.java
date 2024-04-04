package com.sxhta.cloud.gateway.filter;

import com.sxhta.cloud.common.utils.CommonStringUtil;
import com.sxhta.cloud.common.utils.html.EscapeUtil;
import com.sxhta.cloud.gateway.config.properties.XssProperties;
import io.netty.buffer.ByteBufAllocator;
import jakarta.annotation.Nonnull;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.core.io.buffer.DefaultDataBufferFactory;
import org.springframework.core.io.buffer.NettyDataBufferFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpRequestDecorator;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.io.Serial;
import java.io.Serializable;
import java.nio.charset.StandardCharsets;

/**
 * 跨站脚本过滤器
 */
@Singleton
@Component
@ConditionalOnProperty(value = "security.xss.enabled", havingValue = "true")
public final class XssFilter implements GlobalFilter, Ordered, Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    // 跨站脚本的 xss 配置，nacos自行添加
    @Inject
    private XssProperties xss;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        final var request = exchange.getRequest();
        // xss开关未开启 或 通过nacos关闭，不过滤
        if (!xss.getEnabled()) {
            return chain.filter(exchange);
        }
        // GET DELETE 不过滤
        final var method = request.getMethod();
        if (method == HttpMethod.GET || method == HttpMethod.DELETE) {
            return chain.filter(exchange);
        }
        // 非json类型，不过滤
        if (!isJsonRequest(exchange)) {
            return chain.filter(exchange);
        }
        // excludeUrls 不过滤
        final var url = request.getURI().getPath();
        if (CommonStringUtil.matches(url, xss.getExcludeUrls())) {
            return chain.filter(exchange);
        }
        final var httpRequestDecorator = requestDecorator(exchange);
        return chain.filter(exchange.mutate().request(httpRequestDecorator).build());

    }

    private ServerHttpRequestDecorator requestDecorator(ServerWebExchange exchange) {
        return new ServerHttpRequestDecorator(exchange.getRequest()) {

            @Nonnull
            @Override
            public Flux<DataBuffer> getBody() {
                final var body = super.getBody();
                return body.buffer().map(dataBuffers -> {
                    final var dataBufferFactory = new DefaultDataBufferFactory();
                    final var join = dataBufferFactory.join(dataBuffers);
                    final var content = new byte[join.readableByteCount()];
                    join.read(content);
                    DataBufferUtils.release(join);
                    var bodyStr = new String(content, StandardCharsets.UTF_8);
                    // 防xss攻击过滤
                    bodyStr = EscapeUtil.clean(bodyStr);
                    // 转成字节
                    final var bytes = bodyStr.getBytes(StandardCharsets.UTF_8);
                    final var nettyDataBufferFactory = new NettyDataBufferFactory(ByteBufAllocator.DEFAULT);
                    final var buffer = nettyDataBufferFactory.allocateBuffer(bytes.length);
                    buffer.write(bytes);
                    return buffer;
                });
            }

            @Nonnull
            @Override
            public HttpHeaders getHeaders() {
                final var httpHeaders = new HttpHeaders();
                httpHeaders.putAll(super.getHeaders());
                // 由于修改了请求体的body，导致content-length长度不确定，因此需要删除原先的content-length
                httpHeaders.remove(HttpHeaders.CONTENT_LENGTH);
                httpHeaders.set(HttpHeaders.TRANSFER_ENCODING, "chunked");
                return httpHeaders;
            }

        };
    }

    /**
     * 是否是Json请求
     *
     * @param exchange HTTP请求
     */
    public Boolean isJsonRequest(ServerWebExchange exchange) {
        final var header = exchange.getRequest().getHeaders().getFirst(HttpHeaders.CONTENT_TYPE);
        return StringUtils.startsWithIgnoreCase(header, MediaType.APPLICATION_JSON_VALUE);
    }

    @Override
    public int getOrder() {
        return -100;
    }
}
