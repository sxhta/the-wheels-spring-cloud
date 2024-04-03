package com.sxhta.cloud.wheels.remote.domain.owner;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.sxhta.cloud.common.annotation.Excel;
import com.sxhta.cloud.common.web.domain.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;
import java.time.LocalDateTime;

/**
 * 系统访问记录表 sys_logininfor
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class WheelsFrontOwnerLoginInfo extends BaseEntity {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    @Excel(name = "序号", cellType = Excel.ColumnType.NUMERIC)
    private Long infoId;

    /**
     * 用户账号
     */
    @Excel(name = "用户账号")
    private String userName;

    /**
     * 状态 0成功 1失败
     */
    @Excel(name = "状态", readConverterExp = "0=成功,1=失败")
    private String status;

    /**
     * 地址
     */
    @Excel(name = "地址")
    private String ipaddr;

    /**
     * 描述
     */
    @Excel(name = "描述")
    private String msg;

    /**
     * 访问时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "访问时间", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime accessTime;
}