package com.sxhta.cloud.wheels.remote.request.feedback;

import com.baomidou.mybatisplus.annotation.TableField;
import com.sxhta.cloud.common.domain.BaseHashEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@Schema(name = "反馈信息", description = "反馈信息请求体")
public class FeedbackInformationRequest extends BaseHashEntity implements Serializable {

    @Serial
    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

    /**
     * 反馈类型
     */
    private String feedbackTypeHash;

    /**
     * 反馈内容
     */
    private String feedbackContent;

    /**
     * 反馈截图
     */
    private List<String> feedbackPhotograph;
}
