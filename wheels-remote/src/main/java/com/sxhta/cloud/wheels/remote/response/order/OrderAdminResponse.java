package com.sxhta.cloud.wheels.remote.response.order;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Schema(name = "OrderAdminResponse", description = "订单后管列表响应体")
public class OrderAdminResponse implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(description = "ID")
    private Long id;

    @Schema(description = "订单HASH")
    private String hash;

    @Schema(description = "订单编号")
    private String orderNo;

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

    @Schema(description = "下单用户名")
    private String placeOrderUserName;

    @Schema(description = "下单用户点电话")
    private String placeOrderUserPhone;

    @Schema(description = "预约时间")
    @JsonFormat(pattern = "yyyy年M月d日 HH:mm")
    private LocalDateTime appointmentTime;

    @Schema(description = "实际支付金额(真实订单金额)")
    private BigDecimal orderAmount;

    @Schema(description = "创建时间")
    @JsonFormat(pattern = "yyyy年M月d日 HH:mm")
    private LocalDateTime createTime;
}
