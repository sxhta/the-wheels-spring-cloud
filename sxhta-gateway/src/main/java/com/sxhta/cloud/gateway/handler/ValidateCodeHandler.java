package com.sxhta.cloud.gateway.handler;

import com.sxhta.cloud.gateway.service.ValidateCodeService;
import jakarta.annotation.Nonnull;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import lombok.SneakyThrows;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.HandlerFunction;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.io.Serial;
import java.io.Serializable;

/**
 * 验证码获取
 */
@Singleton
@Component
public final class ValidateCodeHandler implements HandlerFunction<ServerResponse>, Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Inject
    private ValidateCodeService validateCodeService;

    @Nonnull
    @Override
    @SneakyThrows
    public Mono<ServerResponse> handle(@Nonnull ServerRequest serverRequest) {
        final var ajax = validateCodeService.createCaptcha();
        final var body = BodyInserters.fromValue(ajax);
        return ServerResponse.status(HttpStatus.OK).body(body);
    }
}
