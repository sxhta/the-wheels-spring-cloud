package com.sxhta.cloud.wheels.websocket.interceptor;

import jakarta.annotation.Nonnull;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.support.HttpSessionHandshakeInterceptor;

import java.util.Map;

@Component
public final class MainHandShakeInterceptor extends HttpSessionHandshakeInterceptor {

    private static final String uriTemplateMain
            = "/websocket/main?own_uid={own_uid}&target_uid={target_uid}";

    @Override
    public boolean beforeHandshake(@Nonnull ServerHttpRequest request,
                                   @Nonnull ServerHttpResponse response,
                                   @Nonnull WebSocketHandler wsHandler,
                                   @Nonnull Map<String, Object> attributes) {


        return true;
    }

    @Override
    public void afterHandshake(@Nonnull ServerHttpRequest request, @Nonnull ServerHttpResponse response, @Nonnull WebSocketHandler wsHandler,
                               Exception exception) {
    }
}
