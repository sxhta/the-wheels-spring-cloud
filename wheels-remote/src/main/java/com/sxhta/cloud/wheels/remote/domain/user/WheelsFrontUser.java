package com.sxhta.cloud.wheels.remote.domain.user;


import com.baomidou.mybatisplus.annotation.TableField;
import com.sxhta.cloud.common.annotation.Excel;
import com.sxhta.cloud.common.domain.AbstractUserEntity;
import com.sxhta.cloud.common.utils.encrypt.EncryptUtil;
import com.sxhta.cloud.common.utils.uuid.UUID;
import com.sxhta.cloud.common.xss.Xss;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.HashMap;
import java.util.Map;

@Data
@EqualsAndHashCode(callSuper = true)
public class WheelsFrontUser extends AbstractUserEntity {

    private String hash = EncryptUtil.generateEntityHash(UUID.randomUUID(true).toString());

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
    @Excel(name = "用户性别", readConverterExp = "0=未设置,1=男,2=女,3=未知")
    private Integer gender = 0;

    /**
     * 用户头像
     */
    private String avatar;

    /**
     * 请求参数
     */
    @TableField(exist = false)
    private Map<String, Object> params = new HashMap<>();
}
