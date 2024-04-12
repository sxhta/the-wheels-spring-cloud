package com.sxhta.cloud.wheels.service.user;

import com.baomidou.mybatisplus.extension.service.IService;
import com.sxhta.cloud.common.service.ICommonService;
import com.sxhta.cloud.wheels.remote.domain.user.WheelsFrontUser;
import com.sxhta.cloud.wheels.request.user.UserRequest;
import com.sxhta.cloud.wheels.request.user.UserSearchRequest;
import com.sxhta.cloud.wheels.response.common.ImageUploadResponse;
import com.sxhta.cloud.wheels.response.user.UserResponse;
import org.springframework.web.multipart.MultipartFile;

public interface UserService extends IService<WheelsFrontUser>,
        ICommonService<UserSearchRequest, UserRequest, UserResponse> {

    ImageUploadResponse uploadImage(MultipartFile file);
}
