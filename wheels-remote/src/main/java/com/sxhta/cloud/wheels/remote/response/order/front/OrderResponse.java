package com.sxhta.cloud.wheels.remote.response.order.front;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 订单管理
 */
@Data
@Schema(name = "OrderResponse", description = "订单管理响应体")
public class OrderResponse implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(description = "ID")
    private Long id;

    @Schema(description = "订单HASH")
    private String hash;

    @Schema(description = "订单状态，1未接单，2已接单，3进行中，4已完成，5取消订单，6待确认")
    private Integer orderStatus;

    @Schema(description = "预约类型，1即时订单，2预约订单")
    private Integer orderType;

    @Schema(description = "是否加急，1是，2否")
    private Integer isUrgent;

    @Schema(description = "出发地")
    private String departure;

    @Schema(description = "目的地")
    private String destination;

    @Schema(description = "实际支付金额(真实订单金额)")
    private BigDecimal orderAmount;

    @Schema(description = "上车时间")
    @JsonFormat(pattern = "yyyy年M月d日 HH:mm")
    private LocalDateTime departureTime;

    @Schema(description = "到达时间")
    @JsonFormat(pattern = "yyyy年M月d日 HH:mm")
    private LocalDateTime arrivalTime;

    @Schema(description = "车辆类型")
    private String carType;
}