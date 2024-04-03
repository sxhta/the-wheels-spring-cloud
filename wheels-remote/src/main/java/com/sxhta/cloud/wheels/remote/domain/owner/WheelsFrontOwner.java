package com.sxhta.cloud.wheels.remote.domain.owner;


import com.sxhta.cloud.common.annotation.Excel;
import com.sxhta.cloud.common.domain.AbstractUserEntity;
import com.sxhta.cloud.common.xss.Xss;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

/**
 * 用户对象 sys_user
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class WheelsFrontOwner extends AbstractUserEntity {

    /**
     * 用户昵称
     */
    @Excel(name = "用户名称")
    @Xss(message = "用户昵称不能包含脚本字符")
    @Size(max = 30, message = "用户昵称长度不能超过30个字符")
    private String nickName;

    /**
     * 用户邮箱
     */
    @Excel(name = "用户邮箱")
    @Email(message = "邮箱格式不正确")
    @Size(max = 50, message = "邮箱长度不能超过50个字符")
    private String email;

    /**
     * 手机号码
     */
    @Excel(name = "手机号码")
    @Size(max = 11, message = "手机号码长度不能超过11个字符")
    private String phone;

    /**
     * 用户性别
     */
    @Excel(name = "用户性别", readConverterExp = "0=男,1=女,2=未知")
    private String sex;

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
