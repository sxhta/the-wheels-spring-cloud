package com.sxhta.cloud.wheels.backend.response.user;

import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serial;
import java.io.Serializable;

@Data
@Accessors(chain = true)
@Schema(name = "司机", description = "司机响应体")
public class DriverUserResponse implements Serializable {

    @Serial
    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

    private Long id;

    private String hash;

    private String username;

    private String nickname;

}
