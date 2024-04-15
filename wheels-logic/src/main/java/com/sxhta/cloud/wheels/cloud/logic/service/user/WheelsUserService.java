package com.sxhta.cloud.wheels.cloud.logic.service.user;

import com.baomidou.mybatisplus.extension.service.IService;
import com.sxhta.cloud.wheels.remote.domain.user.WheelsFrontUser;

import java.util.List;

public interface WheelsUserService extends IService<WheelsFrontUser> {

    WheelsFrontUser getInfoByHash(String userHash);

    List<String> getHashListByUserNameLike(String userName);

    List<String> getHashListByUserPhoneLike(String userPhone);
}