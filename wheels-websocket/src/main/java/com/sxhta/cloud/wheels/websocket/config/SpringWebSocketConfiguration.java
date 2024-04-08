package com.sxhta.cloud.wheels.websocket.config;

import com.sxhta.cloud.wheels.websocket.handler.MainSocketHandler;
import com.sxhta.cloud.wheels.websocket.interceptor.MainHandShakeInterceptor;
import jakarta.annotation.Nonnull;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
import org.springframework.web.socket.server.standard.ServletServerContainerFactoryBean;

import java.io.Serial;
import java.io.Serializable;

@Singleton
@Configuration
@EnableWebSocket
public class SpringWebSocketConfiguration implements WebSocketConfigurer, Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Inject
    private MainSocketHandler mainSocketHandler;

    @Inject
    private MainHandShakeInterceptor mainHandShakeInterceptor;

    @Override
    public void registerWebSocketHandlers(@Nonnull WebSocketHandlerRegistry registry) {
        registry.addHandler(mainSocketHandler, "/websocket/main")
                .addInterceptors(mainHandShakeInterceptor)
                .setAllowedOrigins("*");
    }

    @Bean
    public ServletServerContainerFactoryBean createWebSocketContainer() {
        final var container = new ServletServerContainerFactoryBean();
        // ws 传输数据的时候，数据过大有时候会接收不到，所以在此处设置bufferSize
        container.setMaxTextMessageBufferSize(1024 * 1000);
        container.setMaxBinaryMessageBufferSize(1024 * 1000);
        container.setMaxSessionIdleTimeout(15 * 60000L);
        return container;
    }
}