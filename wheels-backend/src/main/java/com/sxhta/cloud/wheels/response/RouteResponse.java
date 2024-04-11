package com.sxhta.cloud.wheels.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 行程配置表
 */
@Data
@Schema(name = "RouteResponse", description = "行程配置表响应体")
public class RouteResponse implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(description = "id")
    private Long id;

    @Schema(description = "行程配置HASH")
    private String hash;

    @Schema(description = "出发地")
    private String departure;

    @Schema(description = "目的地")
    private String destination;

    @Schema(description = "价格")
    private BigDecimal price;

    @Schema(description = "里程")
    private String mileage;

    @Schema(description = "备注")
    private String mark;

    @Schema(description = "创建时间")
    @JsonFormat(pattern = "yyyy年M月d日 HH:mm")
    private LocalDateTime createTime;
}
