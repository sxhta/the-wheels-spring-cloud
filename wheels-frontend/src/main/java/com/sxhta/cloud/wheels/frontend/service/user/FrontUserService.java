package com.sxhta.cloud.wheels.frontend.service.user;

import com.baomidou.mybatisplus.extension.service.IService;
import com.sxhta.cloud.wheels.remote.domain.user.WheelsFrontUser;
import com.sxhta.cloud.wheels.remote.request.RemoteRegisterRequest;
import com.sxhta.cloud.wheels.remote.response.wheelsUser.WheelsUserResponse;
import com.sxhta.cloud.wheels.remote.vo.FrontUserHashVo;

import java.util.List;

public interface FrontUserService extends IService<WheelsFrontUser> {

    WheelsFrontUser getUserByAccount(String account);

    Boolean register(RemoteRegisterRequest account);

    FrontUserHashVo getHashById(Long id);

    String getHasHById(Long userId);

    List<String> getHashListByUserName(String userName);

    List<String> getHashListByUserPhone(String userPhone);

    WheelsUserResponse getInfoByHash(String userHash);
}
