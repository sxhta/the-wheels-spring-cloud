package com.sxhta.cloud.wheels.order.entity;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.sxhta.cloud.common.utils.encrypt.EncryptUtil;
import com.sxhta.cloud.common.utils.uuid.UUID;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 订单详情
 */
@Data
@TableName("wheels_order_info")
@Schema(name = "OrderInfo", description = "订单详情")
public class OrderInfo implements Serializable{

    @Serial
    private static final long serialVersionUID=1L;

    @TableId(value = "id", type = IdType.AUTO)
    @Schema(description = "ID")
    private Long id;

    @Schema(description = "HASH")
    private String hash = EncryptUtil.generateEntityHash(UUID.randomUUID(true).toString());

    @Schema(description = "订单HASH")
    private String orderHash;

    @Schema(description = "出发地")
    private String departure;

    @Schema(description = "目的地")
    private String destination;

    @Schema(description = "里程数")
    private String mileage;

    @Schema(description = "出发时间")
    private LocalDateTime departureTime;

    @Schema(description = "到达时间")
    private LocalDateTime arrivalTime;

    @Schema(description = "是否帮叫，1是，2否")
    private Integer isHelpCall;

    @Schema(description = "帮叫人姓名")
    private String helpName;

    @Schema(description = "帮叫人电话")
    private String helpPhone;

    @Schema(description = "帮叫人性别，1男，2女")
    private Integer helpSex;

}