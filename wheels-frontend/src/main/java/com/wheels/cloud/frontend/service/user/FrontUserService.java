package com.wheels.cloud.frontend.service.user;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wheels.cloud.frontend.domain.user.FrontUser;

public interface FrontUserService extends IService<FrontUser> {

    FrontUser getUserByAccount(String account);
}
