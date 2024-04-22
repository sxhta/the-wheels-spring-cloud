package com.sxhta.cloud.wheels.backend.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 地区配置表
 */
@Data
@Schema(name = "AreaResponse", description = "地区配置表响应体")
public class AreaResponse implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(description = "ID")
    private Long id;

    @Schema(description = "地区HASH")
    private String hash;

    @Schema(description = "地区名称")
    private String areaName;

    @Schema(description = "创建时间")
    @JsonFormat(pattern = "yyyy年M月d日 HH:mm")
    private LocalDateTime createTime;
}
