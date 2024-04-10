package com.wheels.cloud.frontend.service.user;

import com.baomidou.mybatisplus.extension.service.IService;
import com.sxhta.cloud.wheels.remote.domain.user.WheelsFrontUser;
import com.wheels.cloud.frontend.response.user.AvatarResponse;
import com.wheels.cloud.frontend.response.user.FrontUserInfoResponse;
import org.springframework.web.multipart.MultipartFile;

public interface FrontUserInfoService extends IService<WheelsFrontUser> {

    FrontUserInfoResponse getUserInfoByToken();

    AvatarResponse uploadAvatar(MultipartFile file);
}
