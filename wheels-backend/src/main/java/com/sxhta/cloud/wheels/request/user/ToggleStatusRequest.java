package com.sxhta.cloud.wheels.request.user;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serial;
import java.io.Serializable;

@Data
@Accessors(chain = true)
@Schema(name = "用户搜索", description = "用户搜索请求体")
public class ToggleStatusRequest implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(name = "帐号")
    private String hash;
}
