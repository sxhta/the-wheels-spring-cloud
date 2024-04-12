package com.sxhta.cloud.wheels.remote.response.feedback;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Accessors(chain = true)
@Schema(name = "反馈信息", description = "反馈信息响应体")
public class FeedbackInformationResponse implements Serializable {

    @Serial
    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @TableId
    private Long id;

    /**
     * 反馈hash
     */
    private String hash;

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


}
