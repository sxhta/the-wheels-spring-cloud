package com.wheels.cloud.frontend.service.user.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sxhta.cloud.wheels.remote.domain.user.WheelsFrontUser;
import com.wheels.cloud.frontend.mapper.user.FrontUserMapper;
import com.wheels.cloud.frontend.request.auth.RegisterRequest;
import com.wheels.cloud.frontend.service.user.FrontUserService;
import org.springframework.stereotype.Service;

import java.io.Serial;
import java.io.Serializable;

@Service
public class FrontUserServiceImpl extends ServiceImpl<FrontUserMapper, WheelsFrontUser> implements FrontUserService, Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Override
    public WheelsFrontUser getUserByAccount(String account) {
        final var lqw = new LambdaQueryWrapper<WheelsFrontUser>();
        lqw.eq(WheelsFrontUser::getA)
        return null;
    }

    @Override
    public Boolean register(RegisterRequest account) {
        return null;
    }
}
