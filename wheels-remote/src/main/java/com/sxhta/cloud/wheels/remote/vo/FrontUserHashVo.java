package com.sxhta.cloud.wheels.remote.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

@Data
@Schema(name = "FrontUserInfoVo", description = "用户HASH对象")
public class FrontUserHashVo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;
    @Schema(description = "ID")
    private Long id;

    @Schema(description = "HASH")
    private String hash;
}
