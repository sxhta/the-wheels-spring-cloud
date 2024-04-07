package com.sxhta.cloud.wheels.remote.domain.user;


import com.sxhta.cloud.common.annotation.Excel;
import com.sxhta.cloud.common.domain.AbstractUserEntity;
import com.sxhta.cloud.common.xss.Xss;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

@Data
@EqualsAndHashCode(callSuper = true)
public class WheelsFrontUser extends AbstractUserEntity {

    /**
     * 用户昵称
     */
    @Excel(name = "用户名称")
    @Xss(message = "用户昵称不能包含脚本字符")
    @Size(max = 30, message = "用户昵称长度不能超过30个字符")
    private String account;

    /**
     * 用户性别
     */
    @Excel(name = "用户性别", readConverterExp = "0=男,1=女,2=未知")
    private Integer gender;

    /**
     * 用户头像
     */
    private String avatar;

    /**
     * 最后登录IP
     */
    @Excel(name = "最后登录IP", type = Excel.Type.EXPORT)
    private String loginIp;

    /**
     * 最后登录时间
     */
    @Excel(name = "最后登录时间", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss", type = Excel.Type.EXPORT)
    private LocalDateTime loginDate;
}
