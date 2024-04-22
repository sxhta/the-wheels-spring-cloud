package com.sxhta.cloud.wheels.backend.request.feedback;

import com.baomidou.mybatisplus.annotation.TableField;
import com.sxhta.cloud.common.domain.BaseHashEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serial;
import java.io.Serializable;

@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@Schema(name = "反馈类型", description = "反馈类型请求体")
public class FeedbackTypeRequest extends BaseHashEntity implements Serializable {
    @Serial
    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

    /**
     * 类型名称
     */
    private String name;

    /**
     * 反馈类型状态(0-启用 1-禁用)
     */
    private Boolean status = false;

}
