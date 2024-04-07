package com.sxhta.cloud.wheels.auth.service.sms;

import com.sxhta.cloud.wheels.auth.request.SendCodeRequest;

public interface SmsService {

    Boolean sendCode(SendCodeRequest request);

    void checkValidateCode(String phone, Integer code);
}
