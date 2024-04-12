package com.sxhta.cloud.wheels.request.bank;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serial;
import java.io.Serializable;

@Data
@Accessors(chain = true)
@Schema(name = "用户请求", description = "用户请求体")
public class BankCategoryRequest implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(name = "哈希")
    private String hash;

    @Schema(name = "帐号")
    private String account;

    @Schema(name = "登录名称")
    private String userName;

    @Schema(name = "用户性别")
    private Integer gender = 0;

    @Schema(name = "用户头像")
    private String avatar;
}
