package com.sxhta.cloud.wheels.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.sxhta.cloud.common.domain.BaseHashEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

/**
 * 车主实名认证
 */


@Data
@EqualsAndHashCode(callSuper = true)
@TableName(value = "wheels_real_name_owner")
public class RealNameOwner extends BaseHashEntity implements Serializable {

    @Serial
    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @TableId
    private Long id;

    /**
     * 认证人
     */
    private String ownerHash;

    /**
     * 性别(1 男 2女)
     */
    private Integer gender;

    /**
     * 真实姓名
     */
    private String realName;

    /**
     * 身份证正面
     */
    private String identityCardFrontUrl;

    /**
     * 生份证反面
     */
    private String identityCardBackUrl;

    /**
     * 审核状态(1 待审核 2 审核通过 3 审核驳回)
     */
    private Integer status;

    /**
     * 审核时间
     */
    private Date auditTime;

    /**
     * 审核人
     */
    private String auditBy;

    /**
     * 审核次数
     */
    private Integer auditCount;

    /**
     * 创建者
     */
    private String createBy;

    /**
     * 修改人
     */
    private String updateBy;


}