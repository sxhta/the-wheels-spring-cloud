package com.sxhta.cloud.wheels.auth.constant;

import java.io.Serial;
import java.io.Serializable;

public final class SmsConstants implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    public static final String SMS_VALIDATE_PHONE = "sms:validate:code:";

    //验证码过期时间为1分钟
    public final static Long SMS_LOCK_TIME = 1L;
}