package com.sxhta.cloud.wheels.response.user;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@Accessors(chain = true)
@Schema(name = "用户响应", description = "用户响应体")
public class UserResponse implements Serializable {

    @Schema(name = "用户ID")
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
    private String userName;

    @Schema(name = "账号状态（0正常 1停用）")
    private String status;

    @Schema(name = "创建者")
    private String createBy;

    @Schema(name = "更新者")
    private String updateBy;

    @Schema(name = "备注")
    private String remark;

    @Schema(name = "创建时间")
    private LocalDateTime createTime;

    @Schema(name = "修改时间")
    private LocalDateTime updateTime;

    @Schema(name = "删除时间")
    private LocalDateTime deletedTime;
}