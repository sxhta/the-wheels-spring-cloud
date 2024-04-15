package com.wheels.cloud.order.exportvo;

import com.alibaba.excel.annotation.ExcelProperty;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class AdminOrderExportVo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(description = "ID")
    @ExcelProperty(value = {"${titleName}","${createTime}","ID"}, index = 0)
    private String id;

    @Schema(description = "订单编号")
    @ExcelProperty(value = {"${titleName}","${createTime}","订单编号"}, index = 1)
    private String orderNo;

    @Schema(description = "订单状态，1待确认，2待出行，3已完成，4取消，5进行中")
    @ExcelProperty(value = {"${titleName}","${createTime}","订单状态"}, index = 2)
    private String orderStatus;

    @Schema(description = "预约类型，1即时订单，2预约订单")
    @ExcelProperty(value = {"${titleName}","${createTime}","预约类型"}, index = 3)
    private String orderType;

    @Schema(description = "预约时间")
    @ExcelProperty(value = {"${titleName}","${createTime}","预约时间"}, index = 4)
    @JsonFormat(pattern = "yyyy年M月d日 HH:mm")
    private LocalDateTime appointmentTime;

    @Schema(description = "出行类型，1跨境出行，2接送机")
    @ExcelProperty(value = {"${titleName}","${createTime}","出行类型"}, index = 5)
    private String travelType;

    @Schema(description = "下单用户名")
    @ExcelProperty(value = {"${titleName}","${createTime}","下单人姓名"}, index = 6)
    private String placeOrderUserName;

    @Schema(description = "下单用户点电话")
    @ExcelProperty(value = {"${titleName}","${createTime}","下单人联系方式"}, index = 7)
    private String placeOrderUserPhone;

    @Schema(description = "支付类型，1支付宝，2微信，3余额，4现金支付，5其他")
    @ExcelProperty(value = {"${titleName}","${createTime}","支付类型"}, index = 8)
    private String payType;

    @Schema(description = "支付状态，1待支付，2支付中，3支付成功，4支付失败，5退款中，6退款成功")
    @ExcelProperty(value = {"${titleName}","${createTime}","支付状态"}, index = 9)
    private String payStatus;

    @Schema(description = "原始金额")
    @ExcelProperty(value = {"${titleName}","${createTime}","订单金额"}, index = 10)
    private BigDecimal originAmount;

    @Schema(description = "实际支付金额(真实订单金额)")
    @ExcelProperty(value = {"${titleName}","${createTime}","实际支付金额"}, index = 11)
    private BigDecimal orderAmount;

    @Schema(description = "是否加急，1是，2否")
    @ExcelProperty(value = {"${titleName}","${createTime}","是否加急"}, index = 12)
    private String isUrgent;

    @Schema(description = "加急金额")
    @ExcelProperty(value = {"${titleName}","${createTime}","加急金额"}, index = 13)
    private BigDecimal urgentAmount;

    @Schema(description = "其他费用")
    @ExcelProperty(value = {"${titleName}","${createTime}","其他费用"}, index = 14)
    private BigDecimal otherFee;

    @Schema(description = "是否使用优惠卷，0否，1是")
    @ExcelProperty(value = {"${titleName}","${createTime}","是否使用优惠卷"}, index = 15)
    private String isUseCoupon;

    @Schema(description = "优惠卷金额")
    @ExcelProperty(value = {"${titleName}","${createTime}","优惠卷金额"}, index = 16)
    private BigDecimal couponAmount;

    @Schema(description = "创建时间")
    @JsonFormat(pattern = "yyyy年M月d日 HH:mm")
    @ExcelProperty(value = {"${titleName}","${createTime}","订单创建时间"}, index = 17)
    private LocalDateTime createTime;

    @Schema(description = "出发地")
    @ExcelProperty(value = {"${titleName}","${createTime}","出发地"}, index = 18)
    private String departure;

    @Schema(description = "目的地")
    @ExcelProperty(value = {"${titleName}","${createTime}","目的地"}, index = 19)
    private String destination;

    @Schema(description = "里程数")
    @ExcelProperty(value = {"${titleName}","${createTime}","出行里程数"}, index = 20)
    private String mileage;

    @Schema(description = "出发时间")
    @ExcelProperty(value = {"${titleName}","${createTime}","出发时间"}, index = 21)
    private LocalDateTime departureTime;

    @Schema(description = "到达时间")
    @ExcelProperty(value = {"${titleName}","${createTime}","到达时间"}, index = 22)
    private LocalDateTime arrivalTime;

    @Schema(description = "是否帮叫，1是，2否")
    @ExcelProperty(value = {"${titleName}","${createTime}","是否帮叫"}, index = 23)
    private String isHelpCall;

    @Schema(description = "出行人姓名")
    @ExcelProperty(value = {"${titleName}","${createTime}","出行人姓名"}, index = 24)
    private String travelersUserName;

    @Schema(description = "出行人电话")
    @ExcelProperty(value = {"${titleName}","${createTime}","出行人电话"}, index = 25)
    private String travelersUserPhone;

    @Schema(description = "性别0=未设置,1=男,2=女,3=未知")
    @ExcelProperty(value = {"${titleName}","${createTime}","出行人性别"}, index = 26)
    private String travelersUserSex;

    @Schema(description = "车主姓名")
    @ExcelProperty(value = {"${titleName}","${createTime}","车主姓名"}, index = 27)
    private String ownerUserName;

    @Schema(description = "车主电话")
    @ExcelProperty(value = {"${titleName}","${createTime}","车主电话"}, index = 28)
    private String ownerUserPhone;

    @Schema(description = "车辆类型")
    @ExcelProperty(value = {"${titleName}","${createTime}","车辆类型"}, index = 29)
    private String carType;

    @Schema(description = "是否退款，0否，1是")
    @ExcelProperty(value = {"${titleName}","${createTime}","是否退款"}, index = 30)
    private String isRefund;

    @Schema(description = "退款时间")
    @JsonFormat(pattern = "yyyy年M月d日 HH:mm")
    @ExcelProperty(value = {"${titleName}","${createTime}","退款时间"}, index = 31)
    private LocalDateTime refundTime;

    @Schema(description = "退款金额")
    @ExcelProperty(value = {"${titleName}","${createTime}","退款金额"}, index = 32)
    private BigDecimal refundAmount;
}
