package com.wheels.cloud.frontend.service.user;

import com.baomidou.mybatisplus.extension.service.IService;
import com.sxhta.cloud.wheels.remote.domain.user.WheelsFrontUser;
import com.sxhta.cloud.wheels.remote.vo.FrontUserHashVo;
import com.sxhta.cloud.wheels.remote.request.RemoteRegisterRequest;

public interface FrontUserService extends IService<WheelsFrontUser> {

    WheelsFrontUser getUserByAccount(String account);

    Boolean register(RemoteRegisterRequest account);

    FrontUserHashVo getHashById(Long id);
}
