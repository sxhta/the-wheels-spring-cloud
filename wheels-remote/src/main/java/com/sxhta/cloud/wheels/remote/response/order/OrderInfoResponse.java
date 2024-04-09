package com.sxhta.cloud.wheels.remote.response.order;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;
import java.io.Serializable;

@Data
@EqualsAndHashCode(callSuper = true)
@Schema(name = "OrderInfoResponse", description = "订单详情管理响应体")
public class OrderInfoResponse extends OrderResponse implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
}
