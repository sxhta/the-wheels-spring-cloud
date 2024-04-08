package com.sxhta.cloud.wheels.websocket.service;


import com.sxhta.cloud.wheels.websocket.event.ISocketEvent;

public interface SocketEventService {

    SocketEventService addEvent(String action, ISocketEvent<String> event);

    ISocketEvent<String> getEvent(String action);
}

