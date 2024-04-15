package com.sxhta.cloud.wheels.remote.response.order.owner;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class OrderOwnerResponse  implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(description = "订单HASH")
    private String hash;

    @Schema(description = "预约类型，1即时订单，2预约订单")
    private Integer orderType;

    @Schema(description = "出发地")
    private String departure;

    @Schema(description = "目的地")
    private String destination;

    @Schema(description = "预约时间")
    @JsonFormat(pattern = "yyyy年M月d日 HH:mm")
    private LocalDateTime appointmentTime;

    @Schema(description = "实际支付金额(真实订单金额)")
    private BigDecimal orderAmount;
}
