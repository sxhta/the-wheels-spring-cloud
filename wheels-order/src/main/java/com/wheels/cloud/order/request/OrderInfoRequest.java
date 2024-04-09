package com.wheels.cloud.order.request;


import com.sxhta.cloud.common.domain.BaseHashEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.EqualsAndHashCode;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.Data;

/**
 * 订单详情
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Schema(name = "OrderInfoRequest", description = "订单详情请求体")
public class OrderInfoRequest extends BaseHashEntity implements Serializable{

@Serial
private static final long serialVersionUID=1L;

                    @Schema(description = "ID")
                        private Long id;

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
                        private Boolean isHelpCall;

                                @Schema(description = "帮叫人姓名")
                        private String helpName;

                                @Schema(description = "帮叫人电话")
                        private String helpPhone;

            }