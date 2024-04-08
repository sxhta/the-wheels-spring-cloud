package com.sxhta.cloud.wheels.auth.service.sms.impl;

import cn.hutool.core.util.ObjectUtil;
import com.sxhta.cloud.cache.redis.service.RedisService;
import com.sxhta.cloud.common.component.RandomComponent;
import com.sxhta.cloud.common.exception.ServiceException;
import com.sxhta.cloud.wheels.auth.constant.SmsConstants;
import com.sxhta.cloud.wheels.auth.request.SendCodeRequest;
import com.sxhta.cloud.wheels.auth.service.sms.SmsService;
import jakarta.inject.Inject;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class SmsServiceImpl implements SmsService {

    @Inject
    private RedisService<String, Integer> redisService;

    @Inject
    private RandomComponent randomComponent;

    @Override
    public Boolean sendCode(SendCodeRequest request) {
        final var code = randomComponent.randomCount(111111, 999999);
        //这里做网络请求
        //网络请求结束
        final var phone = request.getPhone();
        final var key = SmsConstants.SMS_VALIDATE_PHONE + phone;
        return redisService.setCacheObject(key, code, SmsConstants.SMS_LOCK_TIME, TimeUnit.MINUTES);
    }

    @Override
    public void checkValidateCode(String phone, Integer code) {
        final var key = SmsConstants.SMS_VALIDATE_PHONE + phone;
        final var validateCode = redisService.getCacheObject(key);
        if (ObjectUtil.isNull(validateCode)) {
            throw new ServiceException("验证码已过期");
        }
        if (!validateCode.equals(code)) {
            throw new ServiceException("验证码错误");
        }
        //删除验证码
        redisService.deleteObject(SmsConstants.SMS_VALIDATE_PHONE + phone);
    }
}
