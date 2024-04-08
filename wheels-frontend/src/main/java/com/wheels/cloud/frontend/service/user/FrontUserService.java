package com.wheels.cloud.frontend.service.user;

import com.baomidou.mybatisplus.extension.service.IService;
import com.sxhta.cloud.wheels.remote.domain.user.WheelsFrontUser;
import com.sxhta.cloud.wheels.remote.request.RegisterRequest;
import com.sxhta.cloud.wheels.remote.vo.FrontUserCacheVo;
import com.sxhta.cloud.wheels.remote.vo.FrontUserHashVo;

public interface FrontUserService extends IService<WheelsFrontUser> {

    WheelsFrontUser getUserByAccount(String account);

    FrontUserCacheVo register(RegisterRequest account);

    FrontUserHashVo getHashById(Long id);
}
