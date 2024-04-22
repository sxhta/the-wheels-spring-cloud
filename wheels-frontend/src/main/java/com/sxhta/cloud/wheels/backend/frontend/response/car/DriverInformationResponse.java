package com.sxhta.cloud.wheels.frontend.response.car;

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
@TableName("wheels_driver_information")
@EqualsAndHashCode(callSuper = true)
@Schema(name = "司机资料", description = "司机资料实体类")
public class DriverInformationResponse extends BaseHashEntity implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(name = "ID")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @Schema(name = "车主hash")
    private String carOwnersHash;

    @Schema(name = "类别(1-个人合作 2-企业合作)")
    private Integer cooperationType = 1;

    @Schema(name = "姓名")
    private String name;

    @Schema(name = "性别(0-男 1-女)")
    private Integer gender = 0;

    @Schema(name = "身份证(港/澳)")
    private String identityCard;

    @Schema(name = "车辆注册地(1-内地 2-香港 3-澳门)")
    private Integer carPlaceRegistration;

    @Schema(name = "内地证件(回乡证/港澳通行证/其他)")
    private String hinterlandCertificate;

    @Schema(name = "手机号(香港/澳门)")
    private String phone;

    @Schema(name = "手机号码(内地)")
    private String telephone;

    @Schema(name = "车辆拍照(1-两地牌 2-三地牌)")
    private Integer licensePlate = 1;

    @Schema(name = "香港车牌")
    private String hongKongLicencePlate;

    @Schema(name = "澳门车牌")
    private String macaoLicensePlate;

    @Schema(name = "内地车牌")
    private String mainlandLicencePlate;

    @Schema(name = "车辆型号")
    private String model;

    @Schema(name = "车辆颜色")
    private String colour;

    @Schema(name = "车辆座位(不含司机)")
    private Integer seat;

    @Schema(name = "车辆类型(1-普通商务车(7/8) 2-高级商务车(7/8) 3-商务专车(4/5) 4-顶级商务车)")
    private Integer carType = 1;

    @Schema(name = "身份证(香港/澳门)正面图片")
    private String identificationCardFile;

    @Schema(name = "内地证件(回乡证/港澳通行证/其他)正面、背面")
    private String mainlandCertificateFile;

    @Schema(name = "驾驶执照(香港/澳门)正面")
    private String driverLicenseFile;

    @Schema(name = "驾驶执照(内地)正面副业")
    private String driverLicenseMainlandFile;

    @Schema(name = "批文卡")
    private String approvalCard;

    @Schema(name = "禁区纸")
    private String restrictedAreaPaper;

    @Schema(name = "保险")
    private String insurance;

    @Schema(name = "车辆照片")
    private String carPhotograph;

    @Schema(name = "地址证明")
    private String address;

    @Schema(name = "审批状态(1-审批中 2-审批通过 3-审批驳回)")
    private Integer approveStatus = 1;

    @Schema(name = "备注")
    private String remark;

    @Schema(name = "创建者")
    private String createBy;

    @Schema(name = "修改者")
    private String updateBy;


}
