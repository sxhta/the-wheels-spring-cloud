package com.sxhta.cloud.wheels.backend.response.feedback;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.sxhta.cloud.common.domain.BaseHashEntity;
import com.sxhta.cloud.wheels.backend.response.user.FrontendUserResponse;
import com.sxhta.cloud.wheels.backend.response.user.SystemUserResponse;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@TableName(value = "wheels_feedback_information")
@Schema(name = "反馈信息", description = "反馈信息实体类")
public class FeedbackInformationResponse extends BaseHashEntity implements Serializable {

    @Serial
    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
    /**
     * id
     */
    @TableId
    private Long id;

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

    /**
     * 反馈人
     */
    private FrontendUserResponse feedbackUser;

    /**
     * 反馈时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime feedbackTime;

    /**
     * 是否处理(0-未处理 1-已处理)
     */
    private Boolean isHandle = false;

    /**
     * 处理人
     */
    private SystemUserResponse handleBy;

    /**
     * 处理时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
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
