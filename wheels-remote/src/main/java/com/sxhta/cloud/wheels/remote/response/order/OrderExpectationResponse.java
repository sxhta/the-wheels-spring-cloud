package com.sxhta.cloud.wheels.remote.response.order;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;


@Data
@Schema(name = "OrderExpectationResponse", description = "订单待出行管理响应体")
public class OrderExpectationResponse implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(description = "ID")
    private Long id;

    @Schema(description = "HASH")
    private String hash;

    @Schema(description = "订单编号")
    private String orderNo;

    @Schema(description = "车辆品牌")
    private String carBrand;

    @Schema(description = "车辆型号")
    private String carModel;

    @Schema(description = "香港车牌")
    private String hongKongLicencePlate;

    @Schema(description = "澳门车牌")
    private String macaoLicensePlate;

    @Schema(description = "内地车牌")
    private String mainlandLicencePlate;

    @Schema(description = "车辆图片")
    private String carImage;

    @Schema(description = "座位数量")
    private Integer seatsNumber;

    @Schema(description = "车辆颜色")
    private String carColor;

    @Schema(description = "车主头像")
    private String carAvatar;

    @Schema(description = "车主姓名")
    private String carOwnerName;

    @Schema(description = "车主联系方式")
    private String carOwnerPhone;

    @Schema(description = "预约时间")
    private LocalDateTime appointmentTime;

    @Schema(description = "出发地")
    private String departure;

    @Schema(description = "目的地")
    private String destination;

    @Schema(description = "车辆类型")
    private String carType;

    @Schema(description = "用户名字")
    private String userName;

    @Schema(description = "性别,1男，2女")
    private Integer sex;

    @Schema(description = "用户电话")
    private String userPhone;
}