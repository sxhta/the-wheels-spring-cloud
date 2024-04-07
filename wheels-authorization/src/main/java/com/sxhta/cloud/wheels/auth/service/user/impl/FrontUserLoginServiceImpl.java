package com.sxhta.cloud.wheels.auth.service.user.impl;


import cn.hutool.core.util.ObjectUtil;
import com.sxhta.cloud.common.constant.SecurityConstants;
import com.sxhta.cloud.common.exception.CommonNullException;
import com.sxhta.cloud.wheels.auth.request.CheckCodeRequest;
import com.sxhta.cloud.wheels.auth.service.sms.SmsService;
import com.sxhta.cloud.wheels.auth.service.user.FrontUserLoginService;
import com.sxhta.cloud.wheels.remote.domain.user.WheelsFrontUser;
import com.sxhta.cloud.wheels.remote.openfeign.user.FrontUserOpenFeign;
import com.sxhta.cloud.wheels.remote.request.RegisterRequest;
import com.sxhta.cloud.wheels.remote.vo.FrontUserCacheVo;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * 登录校验方法
 */
@Slf4j
@Service
public class FrontUserLoginServiceImpl
        implements FrontUserLoginService<FrontUserCacheVo, WheelsFrontUser> {

    private final Logger logger = LoggerFactory.getLogger(FrontUserLoginServiceImpl.class);

    @Inject
    private SmsService smsService;

    @Inject
    private FrontUserOpenFeign frontUserOpenFeign;


    @Override
    public FrontUserCacheVo loginOrRegisterByPhone(CheckCodeRequest request) {
        final var phone = request.getAccount();
        final var code = request.getCode();
//        smsService.checkValidateCode(phone, code);
        final var openFeignUserResponse = frontUserOpenFeign.getUserInfo(phone, SecurityConstants.INNER);
        final var cache = openFeignUserResponse.getData();
        if (ObjectUtil.isNull(cache)) {
            //用户不存在，注册新用户
            logger.info("用户不存在，注册新用户");
            final var wheelsFrontUser = new WheelsFrontUser();
            wheelsFrontUser.setAccount(phone)
                    .setUserName(phone);
            final var registerRequest = new RegisterRequest();
            registerRequest.setAccount(phone);

            final var newCacheResponse = frontUserOpenFeign.register(registerRequest, SecurityConstants.INNER);
            final var newCache = newCacheResponse.getData();
            if (ObjectUtil.isNull(newCache)) {
                throw new CommonNullException("新用户远程调用信息为空");
            }
            return newCache;
        }
        return cache;
    }
}
