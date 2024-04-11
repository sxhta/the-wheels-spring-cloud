package com.sxhta.cloud.wheels.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * 行程配置表
 */
@Data
@Schema(name = "RouteSearchRequest", description = "行程配置表搜索参数")
public class RouteSearchRequest implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(description = "出发地")
    private String departure;

    @Schema(description = "目的地")
    private String destination;
}