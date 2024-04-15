package com.sxhta.cloud.wheels.backend.entity.car;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.sxhta.cloud.common.domain.BaseHashEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serial;
import java.io.Serializable;

@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@TableName(value = "wheels_car_type_category")
public class CarTypeCategory extends BaseHashEntity implements Serializable {

    @Serial
    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @TableId
    private Long id;

    /**
     * 车辆类型hash
     */
    private String carTypeHash;

    /**
     * 车辆型号
     */
    private String model;

    /**
     * 车辆颜色
     */
    private String color;

    /**
     * 车辆座位
     */
    private Integer seat;

    /**
     * 车辆品牌
     */
    private String brand;

    /**
     * 车标
     */
    private String logo;

    /**
     * 车辆展示图
     */
    private String displayImage;

    /**
     * 创建者
     */
    private String createBy;

    /**
     * 修改人
     */
    private String updateBy;

}
