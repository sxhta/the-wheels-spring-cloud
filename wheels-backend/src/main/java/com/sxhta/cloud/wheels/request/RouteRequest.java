package com.sxhta.cloud.wheels.request;


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
@EqualsAndHashCode(callSuper = true)
@Schema(name = "RouteRequest", description = "行程配置表请求体")
public class RouteRequest extends BaseHashEntity implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

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