package com.sxhta.cloud.wheels.frontend.entity.authentication;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.sxhta.cloud.common.domain.BaseHashEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serial;
import java.io.Serializable;

@Data
@Accessors(chain = true)
@TableName("wheels_car_certification")
@EqualsAndHashCode(callSuper = true)
@Schema(name = "车辆认证", description = "车辆认证实体类")
public class CarCertification extends BaseHashEntity implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(name = "ID")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @Schema(name = "车主hash")
    private String carOwnersHash;

    @Schema(name = "类型hash")
    private String typeHash;

    @Schema(name = "二级类别hash")
    private String typeCategory;

    @Schema(name = "车辆拍照(1-两地牌 2-三地牌)")
    private Integer licensePlate = 1;

    @Schema(name = "车辆注册地(1-内地 2-香港 3-澳门)")
    private Integer carPlaceRegistration = 1;

    @Schema(name = "香港车牌")
    private String hongKongLicensePlate;

    @Schema(name = "澳门车牌")
    private String macaoLicensePlate;

    @Schema(name = "内地车牌")
    private String mainlandLicensePlate;

    @Schema(name = "批文卡")
    private String approvalCard;

    @Schema(name = "禁区纸")
    private String restrictedAreaPaper;

    @Schema(name = "保险")
    private String insurance;

    @Schema(name = "车辆照片")
    private String carPhotograph;

    @Schema(name = "审批状态(1-审批中 2-审批通过 3-审批驳回)")
    private Integer approveStatus = 1;

    @Schema(name = "备注")
    private String remark;

    @Schema(name = "创建者")
    private String createBy;

    @Schema(name = "修改者")
    private String updateBy;

}
