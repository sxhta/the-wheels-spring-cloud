package com.sxhta.cloud.wheels.frontend.service.user;

import com.baomidou.mybatisplus.extension.service.IService;
import com.sxhta.cloud.wheels.frontend.request.user.AvatarUpdateRequest;
import com.sxhta.cloud.wheels.frontend.request.user.UserInfoRequest;
import com.sxhta.cloud.wheels.frontend.response.user.AvatarResponse;
import com.sxhta.cloud.wheels.frontend.response.user.FrontUserInfoResponse;
import com.sxhta.cloud.wheels.remote.domain.user.WheelsFrontUser;
import org.springframework.web.multipart.MultipartFile;

public interface FrontUserInfoService extends IService<WheelsFrontUser> {

    String getUserHashByToken();

    WheelsFrontUser getCurrentUserByToken();

    FrontUserInfoResponse getUserInfoByToken();

    AvatarResponse uploadAvatar(MultipartFile file);

    Boolean updateUserAvatar(AvatarUpdateRequest request);

    Boolean updateUserInfo(UserInfoRequest request);
}
