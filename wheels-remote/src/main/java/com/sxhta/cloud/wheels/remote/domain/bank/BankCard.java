package com.sxhta.cloud.wheels.remote.domain.bank;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.sxhta.cloud.common.domain.BaseHashEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

@Data
@EqualsAndHashCode(callSuper = true)
@TableName("bank_category")
public class BankCard extends BaseHashEntity {

    @TableId(value = "id", type = IdType.AUTO)
    @Schema(name = "ID")
    private Long id;

    @Schema(name = "用户哈希")
    private String frontUserHash;

    @Schema(name = "车主哈希")
    private String frontOwnerHash;

    @Schema(name = "类型 1 用户 2 车主")
    private Integer type;

    @Schema(name = "订单编号")
    private String cardNumber;

    @Schema(name = "状态")
    private Integer status;

    @Schema(name = "有效期")
    private LocalDateTime validityTime;

    @Schema(name = "持卡人姓名")
    private String holderName;

    @Schema(name = "自己姓名")
    private String ownName;
}
