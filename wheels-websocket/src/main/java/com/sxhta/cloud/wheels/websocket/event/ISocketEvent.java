package com.sxhta.cloud.wheels.websocket.event;

import com.sxhta.cloud.wheels.websocket.command.SocketCommand;

import java.io.IOException;

public interface ISocketEvent<T> {

    void execute(T args, SocketCommand requestCommand) throws IOException;
}
