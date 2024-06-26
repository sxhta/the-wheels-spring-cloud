package com.sxhta.cloud.wheels.order.request;


import com.sxhta.cloud.common.domain.BaseHashEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 订单管理
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Schema(name = "OrderRequest", description = "订单管理请求体")
public class OrderRequest extends BaseHashEntity implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(description = "ID")
    private Long id;

    @Schema(description = "订单编号")
    private String orderNo;

    @Schema(description = "外部订单号，存类似微信、支付宝的订单号")
    private String outTradeNo;

    @Schema(description = "用户HASH")
    private String userHash;

    @Schema(description = "车主HASH")
    private String ownerHash;

    @Schema(description = "车辆HASH")
    private String carHash;

    @Schema(description = "出行类型，1跨境出行，2接送机")
    private Integer travelType;

    @Schema(description = "航班号")
    private String flightNumber;

    @Schema(description = "订单状态，1待出行，2司机已接单，3进行中，4已完成，5取消订单")
    private Integer orderStatus;

    @Schema(description = "支付类型，1支付宝，2微信，3余额，4现金支付，5其他")
    private Integer payType;

    @Schema(description = "支付状态，1待支付，2支付中，3支付成功，4支付失败，5退款中，6退款成功")
    private Integer payStatus;

    @Schema(description = "退款时间")
    private LocalDateTime refundTime;

    @Schema(description = "退款金额")
    private BigDecimal refundAmount;

    @Schema(description = "是否退款，0否，1是")
    private Boolean isRefund;

    @Schema(description = "是否支付，0否，1是")
    private Boolean isPaid;

    @Schema(description = "预约类型，1即时订单，2预约订单")
    private Boolean orderType;

    @Schema(description = "预约时间")
    private LocalDateTime appointmentTime;

    @Schema(description = "实际支付金额(真实订单金额)")
    private BigDecimal orderAmount;

    @Schema(description = "原始金额")
    private BigDecimal originAmount;

    @Schema(description = "是否加急，1是，2否")
    private Boolean isUrgent;

    @Schema(description = "加急金额")
    private BigDecimal urgentAmount;

    @Schema(description = "其他费用")
    private BigDecimal otherFee;

    @Schema(description = "是否使用优惠卷，1是，2否")
    private Boolean isUseCoupon;

    @Schema(description = "优惠卷CODE")
    private String couponHash;

    @Schema(description = "创建人")
    private String createBy;

    @Schema(description = "修改人")
    private String updateBy;

}