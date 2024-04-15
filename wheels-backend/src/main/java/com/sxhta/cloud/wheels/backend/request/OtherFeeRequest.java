package com.sxhta.cloud.wheels.backend.request;


import com.sxhta.cloud.common.domain.BaseHashEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 其他费用配置表
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Schema(name = "OtherFeeRequest", description = "其他费用配置表请求体")
public class OtherFeeRequest extends BaseHashEntity implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(description = "项目名称")
    private String name;

    @Schema(description = "价格")
    private BigDecimal price;

    @Schema(description = "备注")
    private String remark;
}