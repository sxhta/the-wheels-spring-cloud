package com.sxhta.cloud.wheels.backend.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * 地区配置表
 */
@Data
@Schema(name = "AreaSearchRequest", description = "地区配置表搜索参数")
public class AreaSearchRequest implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(description = "地区名称")
    private String areaName;
}
