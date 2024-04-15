package com.sxhta.cloud.wheels.cloud.logic.service.user.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sxhta.cloud.common.exception.CommonNullException;
import com.sxhta.cloud.wheels.cloud.logic.mapper.user.WheelsFrontUserMapper;
import com.sxhta.cloud.wheels.cloud.logic.service.user.WheelsUserService;
import com.sxhta.cloud.wheels.remote.domain.user.WheelsFrontUser;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Primary
@Service
public class WheelsUserServiceImpl extends ServiceImpl<WheelsFrontUserMapper, WheelsFrontUser> implements WheelsUserService {

    @Override
    public WheelsFrontUser getInfoByHash(String userHash) {
        final var lqw = new LambdaQueryWrapper<WheelsFrontUser>();
        lqw.and(i -> i.like(WheelsFrontUser::getHash, userHash));
        final var frontUser = getOne(lqw);
        if (ObjectUtil.isNull(frontUser)) {
            throw new CommonNullException("该用户不存在！");
        }
        return frontUser;
    }

    @Override
    public List<String> getHashListByUserNameLike(String userName) {
        final var lqw = new LambdaQueryWrapper<WheelsFrontUser>();
        lqw.and(i->i.like(WheelsFrontUser::getUserName,userName));
        final var frontUserList = list(lqw);
        final var hashList = new ArrayList<String>();
        if (CollUtil.isEmpty(frontUserList)) {
            return hashList;
        }
        return frontUserList.stream().map(WheelsFrontUser::getHash).collect(Collectors.toList());
    }

    @Override
    public List<String> getHashListByUserPhoneLike(String userPhone) {
        final var lqw = new LambdaQueryWrapper<WheelsFrontUser>();
        lqw.and(i->i.like(WheelsFrontUser::getAccount,userPhone));
        final var frontUserList = list(lqw);
        final var hashList = new ArrayList<String>();
        if (CollUtil.isEmpty(frontUserList)) {
            return hashList;
        }
        return frontUserList.stream().map(WheelsFrontUser::getHash).collect(Collectors.toList());
    }
}