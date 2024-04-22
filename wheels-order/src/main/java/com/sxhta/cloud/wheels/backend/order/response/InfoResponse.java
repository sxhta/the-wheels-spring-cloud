package com.sxhta.cloud.wheels.order.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 订单详情
 */
@Data
@Schema(name = "OrderInfoResponse", description = "订单详情响应体")
public class InfoResponse implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(description = "ID")
    private Long id;

    @Schema(description = "HASH")
    private String hash;

    @Schema(description = "出发地")
    private String departure;

    @Schema(description = "目的地")
    private String destination;

    @Schema(description = "里程数")
    private String mileage;

    @Schema(description = "出发时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:SS")
    private LocalDateTime departureTime;

    @Schema(description = "到达时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:SS")
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
