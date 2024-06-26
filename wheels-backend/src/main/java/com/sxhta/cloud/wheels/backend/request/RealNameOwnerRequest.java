package com.sxhta.cloud.wheels.backend.request;

import com.baomidou.mybatisplus.annotation.TableId;
import com.sxhta.cloud.common.domain.BaseHashEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;
import java.io.Serializable;

@EqualsAndHashCode(callSuper = true)
@Data
@Schema(name = "实名认证", description = "实名认证请求体")
public class RealNameOwnerRequest extends BaseHashEntity implements Serializable {


    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @TableId
    private Long id;

    /**
     * 审核状态(1 待审核 2 审核通过 3 审核驳回)
     */
    private final Integer status = 1;

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

    @Schema(name = "创建者")
    private String createBy;

}
