package com.sxhta.cloud.wheels.backend.response.complain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.sxhta.cloud.common.domain.BaseHashEntity;
import com.sxhta.cloud.wheels.backend.response.user.DriverUserResponse;
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
@Schema(name = "投诉信息", description = "投诉信息响应体")
public class ComplainInformationResponse extends BaseHashEntity implements Serializable {
    @Serial
    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
    /**
     * id
     */
    private Long id;

    /**
     * 投诉内容
     */
    private String content;

    /**
     * 投诉类型
     */
    private ComplainTypeToInfoResponse complainType;

    /**
     * 投诉图片
     */
    private List<String> complainPhotograph;

    /**
     * 投诉人
     */
    private FrontendUserResponse complainUser;

    /**
     * 投诉时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime complainTime;

    /**
     * 投诉的司机
     */
    private DriverUserResponse complainOwner;

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
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
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
    private FrontendUserResponse createBy;

    /**
     * 修改人
     */
    private SystemUserResponse updateBy;
}
