package com.sxhta.cloud.wheels.request;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * 地区配置表
 */
@Data
@Schema(name = "AreaRequest", description = "地区配置表请求体")
public class AreaRequest implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(description = "地区HASH")
    private String hash;

    @Schema(description = "地区名称")
    private String areaName;

}