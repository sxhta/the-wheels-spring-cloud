package com.sxhta.cloud.wheels.websocket.service;

import jakarta.annotation.Nonnull;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;

public interface SocketCommandService {

    void handleTextMessage(@Nonnull WebSocketSession session, @Nonnull TextMessage message) throws IOException;
}
