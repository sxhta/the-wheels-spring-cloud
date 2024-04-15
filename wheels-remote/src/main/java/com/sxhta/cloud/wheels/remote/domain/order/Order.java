package com.sxhta.cloud.wheels.remote.domain.order;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
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
@TableName("wheels_order")
@EqualsAndHashCode(callSuper = true)
@Schema(name = "Order", description = "订单管理")
public class Order extends BaseHashEntity implements Serializable {



    @Serial
    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
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

    @Schema(description = "订单状态，1待确认，2待出行，3已完成，4取消，5进行中")
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
    private Integer orderType;

    @Schema(description = "预约时间")
    private LocalDateTime appointmentTime;

    @Schema(description = "实际支付金额(真实订单金额)")
    private BigDecimal orderAmount;

    @Schema(description = "原始金额")
    private BigDecimal originAmount;

    @Schema(description = "是否加急，1是，2否")
    private Integer isUrgent;

    @Schema(description = "加急金额")
    private BigDecimal urgentAmount;

    @Schema(description = "其他费用")
    private BigDecimal otherFee;

    @Schema(description = "是否使用优惠卷，0否，1是")
    private Boolean isUseCoupon;

    @Schema(description = "优惠卷CODE")
    private String couponHash;

    @Schema(description = "创建人")
    private String createBy;

    @Schema(description = "修改人")
    private String updateBy;

    @Schema(description = "司机端接状态，1不可接单，2可接单，3成功接单，4取消订单")
    private Integer ownerAcceptStatus;

    @Schema(description = "司机接单时间")
    private LocalDateTime ownerCreateTime;

    @Schema(description = "是否确认，0否，1是")
    private Boolean isConfirm;

    @Schema(description = "是否接单，0否，1是")
    private Boolean isAccept;
}