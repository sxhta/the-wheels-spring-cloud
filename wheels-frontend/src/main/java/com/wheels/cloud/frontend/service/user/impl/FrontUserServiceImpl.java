package com.wheels.cloud.frontend.service.user.impl;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sxhta.cloud.common.exception.CommonNullException;
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
        lqw.eq(WheelsFrontUser::getUserName,account);
        final var user = getOne(lqw);
        if(ObjectUtil.isNull(user)){
            throw new CommonNullException("该用户不存在");
        }
        return user;
    }

    @Override
    public Boolean register(RegisterRequest account) {
        return null;
    }
}
