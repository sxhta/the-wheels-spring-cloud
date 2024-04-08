package com.sxhta.cloud.wheels.websocket.handler;

import com.sxhta.cloud.wheels.websocket.service.SocketCommandService;
import com.sxhta.cloud.wheels.websocket.service.SocketDataService;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import jakarta.annotation.Nonnull;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.AbstractWebSocketHandler;

import java.io.Serial;
import java.io.Serializable;

@Singleton
@Component
public class MainSocketHandler extends AbstractWebSocketHandler implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Inject
    private volatile SocketDataService socketDataService;

    @Inject
    private volatile SocketCommandService socketCommandService;

    /**
     * 建立连接成功
     */
    @Override
    public synchronized void afterConnectionEstablished(@Nonnull WebSocketSession session) throws Exception {
        super.afterConnectionEstablished(session);
        socketDataService.initSocketData(session);
    }

    /**
     * 获取文本信息
     */
    @Override
    protected synchronized void handleTextMessage(@Nonnull WebSocketSession session, @Nonnull TextMessage message) throws Exception {
        super.handleTextMessage(session, message);
        socketCommandService.handleTextMessage(session, message);
    }

    /**
     * 关闭连接
     */
    @Override
    public synchronized void afterConnectionClosed(@Nonnull WebSocketSession session, @Nonnull CloseStatus status) throws Exception {
        super.afterConnectionClosed(session, status);
        socketDataService.clearSocketData(session, status);
    }
}