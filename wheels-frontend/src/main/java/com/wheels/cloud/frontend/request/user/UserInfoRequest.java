package com.wheels.cloud.frontend.request.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Accessors(chain = true)
@Schema(name = "用户请求", description = "用户请求体")
public class UserInfoRequest implements Serializable {

    @Schema(name = "帐号")
    private String account;

    @Schema(name = "用户性别")
    private Integer gender = 0;

    @Schema(name = "用户头像")
    private String avatar;

    @Schema(name = "用户名")
    @JsonProperty("user_name")
    private String userName;

    @Schema(name = "生日")
    private LocalDate birthday;

    @Schema(name = "区域")
    private String region;

    @Schema(name = "余额")
    private BigDecimal balance;

    @Schema(name = "账号状态（0正常 1停用）")
    private String status;

    @Schema(name = "备注")
    private String remark;
}