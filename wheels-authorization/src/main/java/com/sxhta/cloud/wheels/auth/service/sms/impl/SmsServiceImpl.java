package com.sxhta.cloud.wheels.auth.service.sms.impl;

import cn.hutool.core.util.ObjectUtil;
import com.sxhta.cloud.cache.redis.service.RedisService;
import com.sxhta.cloud.common.component.RandomComponent;
import com.sxhta.cloud.common.exception.CommonException;
import com.sxhta.cloud.common.exception.ServiceException;
import com.sxhta.cloud.wheels.auth.constant.SmsConstants;
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
    public Boolean sendCode(String phone) {
        final var code = randomComponent.randomCount(111111, 999999);
        //这里做网络请求
        //网络请求结束
        redisService.setCacheObject(SmsConstants.SMS_VALIDATE_PHONE, code, SmsConstants.SMS_LOCK_TIME, TimeUnit.MINUTES);
        return true;
    }

    @Override
    public void checkValidateCode(String phone, Integer code) {
        final var validateCode = redisService.getCacheObject(SmsConstants.SMS_VALIDATE_PHONE + phone);
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
