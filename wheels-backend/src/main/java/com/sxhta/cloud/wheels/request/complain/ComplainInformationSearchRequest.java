package com.sxhta.cloud.wheels.request.complain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
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
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 投诉内容
     */
    private String content;

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
     * 投诉的司机
     */
    private String complainOwnerHash;

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
     * 处理结果
     */
    private Integer handleResult;

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

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime complainStartTime;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime complainEndTime;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime handleStartTime;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime handleEndTime;


}
