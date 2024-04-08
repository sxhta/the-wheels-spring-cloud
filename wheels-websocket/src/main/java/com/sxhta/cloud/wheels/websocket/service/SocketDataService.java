package com.sxhta.cloud.wheels.websocket.service;

import jakarta.annotation.Nonnull;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.WebSocketSession;

import java.util.Map;

public interface SocketDataService {

    void initSocketData(WebSocketSession session);

    void clearSocketData(@Nonnull WebSocketSession session, @Nonnull CloseStatus status) throws Exception;

    Map<String, WebSocketSession> getAllSessionMap();
}
