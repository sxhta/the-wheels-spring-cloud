package com.sxhta.cloud.wheels.frontend.entity.routeconfig;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.sxhta.cloud.common.domain.BaseHashEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 行程配置表
 */
@Data
@TableName("wheels_route")
@EqualsAndHashCode(callSuper = true)
@Schema(name = "Route", description = "行程配置表")
public class Route extends BaseHashEntity implements Serializable{

    @Serial
    private static final long serialVersionUID=1L;

    @TableId(value = "id", type = IdType.AUTO)
    @Schema(description = "id")
    private Long id;

    @Schema(description = "出发地HASH")
    private String departure;

    @Schema(description = "目的地HASH")
    private String destination;

    @Schema(description = "价格")
    private BigDecimal price;

    @Schema(description = "里程")
    private String mileage;

    @Schema(description = "备注")
    private String mark;

    @Schema(description = "创建人HASH")
    private String createBy;

    @Schema(description = "修改人HASH")
    private String updateBy;

    @Schema(description = "删除人HASH")
    private String deleteBy;

}