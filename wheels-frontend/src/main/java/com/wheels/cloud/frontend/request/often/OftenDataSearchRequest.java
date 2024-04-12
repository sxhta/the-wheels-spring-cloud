package com.wheels.cloud.frontend.request.often;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.sxhta.cloud.common.domain.BaseHashEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serial;
import java.io.Serializable;

@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@Schema(name = "常用资料", description = "常用资料实体类")
public class OftenDataSearchRequest extends BaseHashEntity implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(name = "ID")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @Schema(name = "姓名")
    private String name;

    @Schema(name = "英文")
    private String englishName;

    @Schema(name = "性别(0-男 1-女)")
    private Integer gender;

    @Schema(name = "地区")
    private String address;

    @Schema(name = "电话号码")
    private String phone;

    @Schema(name = "构造者")
    private String fromUserHash;

    @Schema(name = "创建者")
    private String createBy;

    @Schema(name = "修改者")
    private String updateBy;
}
