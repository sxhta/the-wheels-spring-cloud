package com.wheels.cloud.order.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 订单管理
 */
@Data
@Schema(name = "OrderSearchRequest", description = "订单管理搜索参数")
public class OrderSearchRequest implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(description = "ID")
    private Long id;

    @Schema(description = "订单编号")
    private String orderNo;

    @Schema(description = "下单人用户名")
    private String placeOrderUserName;

    @Schema(description = "下单人手机号")
    private String placeOrderUserPhone;

    @Schema(description = "订单状态，1未接单，2已接单，3进行中，4已完成，5取消订单，6待确认")
    private Integer orderStatus;

    @Schema(description = "预约类型，1即时订单，2预约订单")
    private Integer orderType;

    @Schema(description = "是否加急，1是，2否")
    private Integer isUrgent;

    @Schema(description = "预约开始时间")
    private LocalDateTime reservationStartTime;

    @Schema(description = "预约结束时间")
    private LocalDateTime reservationEndTime;

    @Schema(description = "订单创建时间")
    private LocalDateTime createStartTime;

    @Schema(description = "订单结束时间")
    private LocalDateTime createEndTime;
}