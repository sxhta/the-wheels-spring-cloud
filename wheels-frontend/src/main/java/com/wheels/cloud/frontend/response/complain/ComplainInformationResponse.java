package com.wheels.cloud.frontend.response.complain;

import com.baomidou.mybatisplus.annotation.IdType;
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
@Schema(name = "我的投诉", description = "我的投诉相应体")
public class ComplainInformationResponse extends BaseHashEntity implements Serializable {
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
    private LocalDateTime complainTime = LocalDateTime.now();

    /**
     * 投诉的司机
     */
    private String complainOwnerHash;

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


}
