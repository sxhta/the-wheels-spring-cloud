package com.sxhta.cloud.wheels.websocket.service.impl;

import com.sxhta.cloud.wheels.websocket.event.HeartbeatPingEvent;
import com.sxhta.cloud.wheels.websocket.event.HeartbeatPongEvent;
import com.sxhta.cloud.wheels.websocket.event.ISocketEvent;
import com.sxhta.cloud.wheels.websocket.service.SocketEventService;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import org.springframework.stereotype.Service;

@Singleton
@Service
public class SocketEventServiceImpl implements SocketEventService {

    @Inject
    private volatile HeartbeatPingEvent heartbeatPingEvent;

    @Inject
    private volatile HeartbeatPongEvent heartbeatPongEvent;


    @Override
    public SocketEventService addEvent(String action, ISocketEvent<String> event) {
        return null;
    }

    @Override
    public ISocketEvent<String> getEvent(String action) {
        return null;
    }
}
