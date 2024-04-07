package com.wheels.cloud.frontend.service.user;

import com.baomidou.mybatisplus.extension.service.IService;
import com.sxhta.cloud.wheels.remote.domain.user.WheelsFrontUser;
import com.wheels.cloud.frontend.request.auth.RegisterRequest;

public interface FrontUserService extends IService<WheelsFrontUser> {

    WheelsFrontUser getUserByAccount(String account);

    Boolean register(RegisterRequest account);
}
