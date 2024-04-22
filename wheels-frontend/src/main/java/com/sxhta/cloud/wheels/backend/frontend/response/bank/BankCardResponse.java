package com.sxhta.cloud.wheels.frontend.response.bank;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@Schema(name = "头像上传", description = "头像上传响应体")
public final class BankCardResponse implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    @Schema(name = "ID")
    private Long id;

    @Schema(name = "订单编号")
    private String card_number;

    @Schema(name = "状态")
    private Integer status;

    @Schema(name = "有效期")
    private LocalDateTime validityTime;

    @Schema(name = "持卡人姓名")
    private String holderName;

    @Schema(name = "自己姓名")
    private String ownName;
}
