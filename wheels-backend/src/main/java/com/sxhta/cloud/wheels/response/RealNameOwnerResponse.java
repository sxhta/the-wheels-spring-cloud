package com.sxhta.cloud.wheels.response;


import com.baomidou.mybatisplus.annotation.TableId;
import com.sxhta.cloud.common.domain.BaseHashEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;


@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@Schema(name = "实名认证", description = "实名认证响应体")
public class RealNameOwnerResponse extends BaseHashEntity implements Serializable {
    @Serial
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
