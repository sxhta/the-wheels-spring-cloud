package com.sxhta.cloud.wheels.remote.response.order.admin;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@EqualsAndHashCode(callSuper = true)
@Schema(name = "OrderAdminInfoResponse", description = "订单后管详情响应体")
public class OrderAdminInfoResponse extends OrderAdminResponse implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(description = "出行类型，1跨境出行，2接送机")
    private Integer travelType;

    @Schema(description = "航班号")
    private String flightNumber;

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

    @Schema(description = "原始金额")
    private BigDecimal originAmount;

    @Schema(description = "加急金额")
    private BigDecimal urgentAmount;

    @Schema(description = "其他费用")
    private BigDecimal otherFee;

    @Schema(description = "是否使用优惠卷，0否，1是")
    private Boolean isUseCoupon;

    @Schema(description = "优惠卷金额")
    private BigDecimal couponAmount;

    @Schema(description = "里程数")
    private String mileage;

    @Schema(description = "出发时间")
    private LocalDateTime departureTime;

    @Schema(description = "到达时间")
    private LocalDateTime arrivalTime;

    @Schema(description = "是否帮叫，1是，2否")
    private Integer isHelpCall;

    @Schema(description = "出行人姓名")
    private String travelersUserName;

    @Schema(description = "出行人电话")
    private String travelersUserPhone;

    @Schema(description = "性别0=未设置,1=男,2=女,3=未知")
    private Integer travelersUserSex;

    @Schema(description = "车主姓名")
    private String ownerUserName;

    @Schema(description = "车主电话")
    private String ownerUserPhone;

    @Schema(description = "车主头像")
    private String ownerUserAvatar;

    @Schema(description = "车辆类型")
    private String carType;
}
