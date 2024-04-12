package com.wheels.cloud.frontend.response.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
@Accessors(chain = true)
@Schema(name = "用户响应", description = "用户响应体")
public class FrontUserInfoResponse implements Serializable {

    @Schema(name = "用户ID")
    @JsonProperty("user_id")
    private Long userId;

    @Schema(name = "哈希")
    private String hash;

    @Schema(name = "帐号")
    private String account;

    @Schema(name = "用户性别")
    private Integer gender = 0;

    @Schema(name = "用户头像")
    private String avatar;

    @Schema(name = "用户名")
    @JsonProperty("user_name")
    private String userName;

    @Schema(name = "昵称")
    private String nickname;

    @Schema(name = "余额")
    private BigDecimal balance;

    @Schema(name = "账号状态（0正常 1停用）")
    private String status;

    @Schema(name = "备注")
    private String remark;

    @Schema(name = "性别文字描述")
    @JsonProperty("gender_text")
    private String genderText;

    public String getGenderText() {
        return switch (gender) {
            case 0 -> "男";
            case 1 -> "女";
            default -> "未知";
        };
    }
}