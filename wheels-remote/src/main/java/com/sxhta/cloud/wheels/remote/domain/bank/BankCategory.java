package com.sxhta.cloud.wheels.remote.domain.bank;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.sxhta.cloud.common.domain.BaseHashEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@TableName("bank_category")
public class BankCategory extends BaseHashEntity {

    @TableId(value = "id", type = IdType.AUTO)
    @Schema(description = "ID")
    private Long id;

    @Schema(description = "名称")
    private String name;

    @Schema(description = "状态")
    private Integer status;

    @Schema(description = "缩略图")
    private String thumb;
}
