package com.sxhta.cloud.wheels.backend.request.car;

import com.baomidou.mybatisplus.annotation.TableField;
import com.sxhta.cloud.common.domain.BaseHashEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serial;
import java.io.Serializable;

@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
public class CarTypeRequest extends BaseHashEntity implements Serializable {


    @Serial
    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    private Long id;

    /**
     * 类型名称
     */
    private String name;

    /**
     * 类型状态(0.启用 1.禁用)
     */
    private Integer status;

    /**
     * 创建者
     */
    private String createBy;

    /**
     * 修改人
     */
    private String updateBy;

}
