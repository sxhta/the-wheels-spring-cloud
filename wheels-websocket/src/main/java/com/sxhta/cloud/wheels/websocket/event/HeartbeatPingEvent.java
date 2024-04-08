package com.sxhta.cloud.wheels.websocket.event;

import com.sxhta.cloud.wheels.websocket.command.SocketCommand;
import jakarta.inject.Singleton;
import org.springframework.stereotype.Component;

import java.io.Serial;
import java.io.Serializable;

@Singleton
@Component
public class HeartbeatPingEvent implements ISocketEvent<String>, Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Override
    public void execute(String args, SocketCommand requestCommand) {
        System.out.println(args);
        System.out.println(requestCommand);
    }
}