package com.sxhta.cloud.wheels.remote.response.wheelsUser;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

@Data
@Schema(name = "WheelsUserResponse", description = "用户详情管理响应体")
public class WheelsUserResponse implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private Long userId;

    private String hash;

    private String userName;

    private String account;
}
