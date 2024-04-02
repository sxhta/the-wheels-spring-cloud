package com.wheels.cloud.frontend.service.user.impl;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sxhta.cloud.common.exception.CommonNullException;
import com.wheels.cloud.frontend.domain.user.FrontUser;
import com.wheels.cloud.frontend.mapper.user.FrontUserMapper;
import com.wheels.cloud.frontend.service.user.FrontUserService;
import org.springframework.stereotype.Service;

@Service
public class FrontUserServiceImpl extends ServiceImpl<FrontUserMapper, FrontUser> implements FrontUserService {

    @Override
    public FrontUser getUserByAccount(String account) {
        final var lqw = new LambdaQueryWrapper<FrontUser>();
        lqw.eq(FrontUser::getAccount, account);
        final var frontUser = getOne(lqw);
        if (ObjectUtil.isNull(frontUser)) {
            throw new CommonNullException("用户-" + account + "-不存在");
        }
        return frontUser;
    }
}
