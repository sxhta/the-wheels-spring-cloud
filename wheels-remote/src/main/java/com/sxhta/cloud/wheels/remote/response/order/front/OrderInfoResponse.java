package com.sxhta.cloud.wheels.remote.response.order.front;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@EqualsAndHashCode(callSuper = true)
@Schema(name = "OrderInfoResponse", description = "订单详情管理响应体")
public class OrderInfoResponse extends OrderResponse implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(description = "订单编号")
    private String orderNo;

    @Schema(description = "0=未设置,1=男,2=女,3=未知")
    private Integer sex;

    @Schema(description = "用户名字")
    private String userName;

    @Schema(description = "用户电话")
    private String userPhone;

    @Schema(description = "创建时间")
    @JsonFormat(pattern = "yyyy年M月d日 HH:mm")
    private LocalDateTime orderCreateTime;

    @Schema(description = "其他费用")
    private BigDecimal otherFee;

    @Schema(description = "优惠卷金额")
    private BigDecimal couponAmount;

    @Schema(description = "支付类型，1支付宝，2微信，3余额，4现金支付，5其他")
    private Integer payType;

    @Schema(description = "原始金额")
    private BigDecimal originAmount;
}
