package com.sxhta.cloud.wheels.frontend.entity.user;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.sxhta.cloud.common.domain.BaseHashEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serial;
import java.io.Serializable;

@Data
@Accessors(chain = true)
@TableName("wheels_owner_user")
@EqualsAndHashCode(callSuper = true)
@Schema(name = "司机(车主)", description = "司机(车主)实体类")
public class OwnerUser extends BaseHashEntity implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    @Schema(name = "ID")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @Schema(name = "用户名")
    private String username;

    @Schema(name = "密码")
    private String password;

    @Schema(name = "手机号")
    private String account;

    @Schema(name = "是否实名认证(0-否  1-是)")
    private Boolean isReal = false;

    @Schema(name = "状态(0-启用 1-禁用)")
    private Integer status = 0;

    @Schema(name = "昵称")
    private String nickname;

    @Schema(name = "头像")
    private String avatar;

    @Schema(name = "创建者")
    private String createBy;

    @Schema(name = "修改者")
    private String updateBy;

}
