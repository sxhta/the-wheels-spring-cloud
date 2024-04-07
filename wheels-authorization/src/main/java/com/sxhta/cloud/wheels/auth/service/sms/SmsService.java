package com.sxhta.cloud.wheels.auth.service.sms;

public interface SmsService {

    Boolean sendCode(String phone);

    void checkValidateCode(String phone, Integer code);
}
