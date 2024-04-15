package com.sxhta.cloud.wheels.backend.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * 其他费用配置表
 */
@Data
@Schema(name = "OtherFeeSearchRequest", description = "其他费用配置表搜索参数")
public class OtherFeeSearchRequest implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(description = "项目名称")
    private String name;
}
