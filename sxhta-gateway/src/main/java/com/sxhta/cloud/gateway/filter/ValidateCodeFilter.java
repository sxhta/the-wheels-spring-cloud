package com.sxhta.cloud.gateway.filter;

import com.alibaba.fastjson2.JSON;
import com.sxhta.cloud.common.component.ServletComponent;
import com.sxhta.cloud.gateway.config.properties.CaptchaProperties;
import com.sxhta.cloud.gateway.service.ValidateCodeService;
import jakarta.inject.Inject;
import org.apache.commons.lang3.StringUtils;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;

import java.io.Serial;
import java.io.Serializable;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.atomic.AtomicReference;

/**
 * 验证码过滤器
 */
@Component
public class ValidateCodeFilter extends AbstractGatewayFilterFactory<Object> implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private final static String[] VALIDATE_URL = new String[]{"/auth/login", "/auth/register"};

    @Inject
    private ValidateCodeService validateCodeService;

    @Inject
    private CaptchaProperties captchaProperties;

    @Inject
    private ServletComponent servletComponent;

    private static final String CODE = "code";

    private static final String UUID = "uuid";

    @Override
    public GatewayFilter apply(Object config) {
        return (exchange, chain) -> {
            final var request = exchange.getRequest();

            // 非登录/注册请求或验证码关闭，不处理
            if (!StringUtils.containsAnyIgnoreCase(request.getURI().getPath(), VALIDATE_URL) || !captchaProperties.getEnabled()) {
                return chain.filter(exchange);
            }

            try {
                final var rspStr = resolveBodyFromRequest(request);
                final var obj = JSON.parseObject(rspStr);
                validateCodeService.checkCaptcha(obj.getString(CODE), obj.getString(UUID));
            } catch (Exception e) {
                return servletComponent.webFluxResponseWriter(exchange.getResponse(), e.getMessage());
            }
            return chain.filter(exchange);
        };
    }

    private String resolveBodyFromRequest(ServerHttpRequest serverHttpRequest) {
        // 获取请求体
        final var body = serverHttpRequest.getBody();
        final var bodyRef = new AtomicReference<String>();
        body.subscribe(buffer -> {
            final var charBuffer = StandardCharsets.UTF_8.decode(buffer.toByteBuffer());
            DataBufferUtils.release(buffer);
            bodyRef.set(charBuffer.toString());
        });
        return bodyRef.get();
    }
}
