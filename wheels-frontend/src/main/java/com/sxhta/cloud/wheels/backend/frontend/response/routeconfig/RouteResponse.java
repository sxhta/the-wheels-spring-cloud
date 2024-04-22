package com.sxhta.cloud.wheels.frontend.response.routeconfig;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * 行程配置表
 */
@Data
@Schema(name = "RouteResponse", description = "前端地点响应体")
public class RouteResponse implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(description = "地点HASH")
    private String areaHash;

    @Schema(description = "地点名称")
    private String areaName;
}
