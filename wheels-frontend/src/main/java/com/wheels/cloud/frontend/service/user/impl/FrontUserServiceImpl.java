package com.wheels.cloud.frontend.service.user.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sxhta.cloud.wheels.remote.domain.user.WheelsFrontUser;
import com.sxhta.cloud.wheels.remote.request.RegisterRequest;
import com.wheels.cloud.frontend.mapper.user.FrontUserMapper;
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
        lqw.eq(WheelsFrontUser::getUserName,account);
        final var user = getOne(lqw);
        return user;
    }

    @Override
    public Boolean register(RegisterRequest request) {
        final var account = request.getAccount();
        final var wheelsFrontUser = new WheelsFrontUser();
        //通过手机号注册的用户名默认和account 相同
        wheelsFrontUser.setAccount(account)
                .setUserName(account);
        return true;
    }
}
