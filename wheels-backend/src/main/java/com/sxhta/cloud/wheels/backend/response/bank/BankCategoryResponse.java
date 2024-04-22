package com.sxhta.cloud.wheels.backend.response.bank;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@Schema(name = "头像上传", description = "头像上传响应体")
public final class BankCategoryResponse implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    @Schema(description = "ID")
    private Long id;

    @Schema(description = "银行名")
    private String name;

    @Schema(description = "状态")
    private Integer status;

    @Schema(description = "缩略图")
    private String thumb;

    @Schema(description = "备注")
    private String remark;

    @Schema(name = "创建时间")
    private LocalDateTime createTime;

    @Schema(name = "修改时间")
    private LocalDateTime updateTime;

    @Schema(name = "删除时间")
    private LocalDateTime deletedTime;
}
