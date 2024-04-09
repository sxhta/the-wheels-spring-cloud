package com.wheels.cloud.order.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.sxhta.cloud.common.domain.BaseHashEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 订单详情
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Schema(name = "OrderInfoResponse", description = "订单详情响应体")
public class OrderInfoResponse extends BaseHashEntity implements Serializable {

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
}