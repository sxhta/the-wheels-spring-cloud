package com.sxhta.cloud.wheels.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 其他费用配置表
 */
@Data
@Schema(name = "OtherFeeResponse", description = "其他费用配置表响应体")
public class OtherFeeResponse implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(description = "ID")
    private Long id;

    @Schema(description = "费用配置HASH")
    private String hash;

    @Schema(description = "项目名称")
    private String name;

    @Schema(description = "价格")
    private BigDecimal price;

    @Schema(description = "备注")
    private String remark;

    @Schema(description = "创建时间")
    @JsonFormat(pattern = "yyyy年M月d日 HH:mm")
    private LocalDateTime createTime;
}
