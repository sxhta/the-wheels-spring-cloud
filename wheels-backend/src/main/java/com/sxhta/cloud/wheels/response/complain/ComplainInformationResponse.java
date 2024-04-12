package com.sxhta.cloud.wheels.response.complain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.sxhta.cloud.common.domain.BaseHashEntity;
import com.sxhta.cloud.wheels.entity.complain.ComplainType;
import com.sxhta.cloud.wheels.remote.domain.user.WheelsFrontUser;
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
@TableName(value = "wheels_complain")
@Schema(name = "投诉信息", description = "投诉信息实体类")
public class ComplainInformationResponse extends BaseHashEntity implements Serializable {
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
    private String content;

    /**
     * 投诉类型
     */
    private ComplainType complainType;

    /**
     * 投诉图片
     */
    private List<String> complainPhotograph;

    /**
     * 投诉人
     */
    private WheelsFrontUser complainUser;

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
    private Boolean isHandle;

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
