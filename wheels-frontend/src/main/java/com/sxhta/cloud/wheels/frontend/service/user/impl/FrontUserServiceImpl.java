package com.sxhta.cloud.wheels.frontend.service.user.impl;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sxhta.cloud.common.exception.CommonException;
import com.sxhta.cloud.common.exception.CommonNullException;
import com.sxhta.cloud.wheels.cloud.logic.mapper.user.WheelsFrontUserMapper;
import com.sxhta.cloud.wheels.frontend.service.user.FrontUserService;
import com.sxhta.cloud.wheels.remote.domain.user.WheelsFrontUser;
import com.sxhta.cloud.wheels.remote.request.RemoteRegisterRequest;
import com.sxhta.cloud.wheels.remote.vo.FrontUserHashVo;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.io.Serial;
import java.io.Serializable;

@Service
public class FrontUserServiceImpl extends ServiceImpl<WheelsFrontUserMapper, WheelsFrontUser> implements FrontUserService, Serializable {

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

    @Override
    public String getHasHById(Long userId) {
        final var lqw = new LambdaQueryWrapper<WheelsFrontUser>();
        lqw.eq(WheelsFrontUser::getUserId,userId);
        final var frontUser = getOne(lqw);
        if (ObjectUtil.isNull(frontUser)) {
            throw new CommonNullException("该用户不存在！");
        }
        return frontUser.getHash();
    }
}
