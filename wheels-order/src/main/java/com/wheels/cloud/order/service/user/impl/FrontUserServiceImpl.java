package com.wheels.cloud.order.service.user.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sxhta.cloud.wheels.remote.domain.user.WheelsFrontUser;
import com.sxhta.cloud.wheels.remote.request.RemoteRegisterRequest;
import com.wheels.cloud.order.mapper.user.FrontUserMapper;
import com.wheels.cloud.order.service.user.FrontUserService;
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
        return getOne(lqw);
    }

    @Override
    public Boolean register(RemoteRegisterRequest request) {
        final var account = request.getAccount();
        final var wheelsFrontUser = new WheelsFrontUser();
        //通过手机号注册的用户名默认和account 相同
        wheelsFrontUser.setAccount(account)
                .setUserName(account);
        return save(wheelsFrontUser);
    }
}
