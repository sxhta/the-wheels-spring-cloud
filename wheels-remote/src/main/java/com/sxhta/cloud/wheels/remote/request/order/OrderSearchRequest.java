package com.sxhta.cloud.wheels.remote.request.order;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * 订单管理
 */
@Data
@Schema(name = "OrderSearchRequest", description = "订单管理搜索参数")
public class OrderSearchRequest implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(description = "订单编号")
    private String orderNo;

    @Schema(description = "下单人用户名")
    private String placeOrderUserName;

    @Schema(description = "下单人手机号")
    private String placeOrderUserPhone;

    @Schema(description = "订单状态，1待确认，2待出行，3已完成，4取消，5进行中")
    private Integer orderStatus;

    @Schema(description = "预约类型，1即时订单，2预约订单")
    private Integer orderType;

    @Schema(description = "是否加急，1是，2否")
    private Integer isUrgent;

    @Schema(description = "预约开始时间")
    private String reservationStartTime;

    @Schema(description = "预约结束时间")
    private String reservationEndTime;

    @Schema(description = "订单创建时间")
    private String createStartTime;

    @Schema(description = "订单结束时间")
    private String createEndTime;
}