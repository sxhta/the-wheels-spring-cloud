package com.sxhta.cloud.wheels.backend.response.user;

import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serial;
import java.io.Serializable;

@Data
@Accessors(chain = true)
@Schema(name = "系统用户", description = "系统用户响应体")
public class SystemUserResponse implements Serializable {

    @Serial
    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

    private Long id;

    private String hash;

    private String username;

    private String nickname;

}
