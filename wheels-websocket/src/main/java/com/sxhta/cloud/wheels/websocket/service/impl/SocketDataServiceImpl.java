package com.sxhta.cloud.wheels.websocket.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.alibaba.ttl.TransmittableThreadLocal;
import com.sxhta.cloud.wheels.websocket.service.SocketDataService;
import jakarta.annotation.Nonnull;
import jakarta.inject.Singleton;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.WebSocketSession;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Singleton
@Service
public class SocketDataServiceImpl implements SocketDataService {

    /**
     * 本地线程存储（必要）
     */
    @Singleton
    private final ThreadLocal<WebSocketSession> sessions = new TransmittableThreadLocal<>();

    /**
     * 所有的session集合（必要）
     */
    @Singleton
    private final Map<String, WebSocketSession> allSessionMap = new ConcurrentHashMap<>();

    @Override
    public void initSocketData(WebSocketSession session) {
        //本地线程保存
        sessions.set(session);
        final var sessionId = session.getId();
        allSessionMap.put(sessionId, session);
    }

    @Override
    public void clearSocketData(@Nonnull WebSocketSession session, @Nonnull CloseStatus status) throws Exception {
        session.close();
        final var sessionId = session.getId();
        //清除掉所有session中对应的链接
        final var removedSession = allSessionMap.remove(sessionId);
        if (ObjectUtil.isNull(removedSession)) {
            System.out.println("删除失败");
        }
    }

    @Override
    public Map<String, WebSocketSession> getAllSessionMap() {
        return allSessionMap;
    }
}
