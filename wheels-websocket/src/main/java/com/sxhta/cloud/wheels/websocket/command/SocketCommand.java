package com.sxhta.cloud.wheels.websocket.command;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class SocketCommand implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private String action;

    private String message;

    private String data;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime time = LocalDateTime.now();
}
