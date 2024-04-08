package com.wheels.cloud.frontend.service.user.impl;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sxhta.cloud.common.exception.CommonException;
import com.sxhta.cloud.wheels.remote.domain.user.WheelsFrontUser;
import com.sxhta.cloud.wheels.remote.request.RemoteRegisterRequest;
import com.sxhta.cloud.wheels.remote.request.RegisterRequest;
import com.sxhta.cloud.wheels.remote.vo.FrontUserCacheVo;
import com.sxhta.cloud.wheels.remote.vo.FrontUserHashVo;
import com.wheels.cloud.frontend.mapper.user.FrontUserMapper;
import com.wheels.cloud.frontend.service.user.FrontUserService;
import org.springframework.beans.BeanUtils;
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
        lqw.eq(WheelsFrontUser::getUserName, account);
        return getOne(lqw);
    }

    @Override
    public Boolean register(RemoteRegisterRequest request) {
        final var account = request.getAccount();
        final var wheelsFrontUser = new WheelsFrontUser();
        //通过手机号注册的用户名默认和account 相同
        wheelsFrontUser.setAccount(account);
        wheelsFrontUser.setUserName(account);
        return save(wheelsFrontUser);
    }

    @Override
    public FrontUserHashVo getHashById(Long id) {
        final var frontUser = getById(id);
        if (ObjectUtil.isNull(frontUser)) {
            throw new CommonException("该用户不存在！");
        }
        final var response = new FrontUserHashVo();
        BeanUtils.copyProperties(frontUser,response);
        return response;
    }
}
