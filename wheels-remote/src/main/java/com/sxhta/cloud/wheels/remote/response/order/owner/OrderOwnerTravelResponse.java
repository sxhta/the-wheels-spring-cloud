package com.sxhta.cloud.wheels.remote.response.order.owner;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class OrderOwnerTravelResponse implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(description = "订单HASH")
    private String hash;

    @Schema(description = "出发地")
    private String departure;

    @Schema(description = "目的地")
    private String destination;

    @Schema(description = "预约时间")
    @JsonFormat(pattern = "yyyy年M月d日 HH:mm")
    private LocalDateTime appointmentTime;

    @Schema(description = "航班号")
    private String flightNumber;

    @Schema(description = "出行人姓名")
    private String travelName;

    @Schema(description = "出行人电话")
    private String travelPhone;
}
