package com.sxhta.cloud.wheels.request.complain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.sxhta.cloud.common.domain.BaseHashEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@Schema(name = "投诉信息", description = "投诉信息实体类")
public class ComplainInformationSearchRequest extends BaseHashEntity implements Serializable {
    @Serial
    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
    /**
     * id
     */
    @TableId
    private Long id;
    /**
     * 投诉内容
     */
    private String complainContent;
    /**
     * 投诉类型
     */
    private String complainTypeHash;
    /**
     * 投诉图片
     */
    private String complainPhotograph;
    /**
     * 投诉人
     */
    private String complainUser;
    /**
     * 投诉时间
     */
    private LocalDateTime complainTime;
    /**
     * 投诉端(1.用户投诉 2.司机投诉)
     */
    private Integer complainant;
    /**
     * 是否处理(0-未处理 1-已处理)
     */
    private Boolean isHandle = false;
    /**
     * 处理人
     */
    private String handleBy;
    /**
     * 处理时间
     */
    private LocalDateTime handleTime;
    /**
     * 处理备注
     */
    private String handleRemark;

    /**
     * 创建者
     */
    private String createBy;

    /**
     * 修改人
     */
    private String updateBy;
}
