package com.sxhta.cloud.wheels.backend.service.user;

import com.baomidou.mybatisplus.extension.service.IService;
import com.sxhta.cloud.common.service.ICommonService;
import com.sxhta.cloud.wheels.remote.domain.user.WheelsFrontUser;
import com.sxhta.cloud.wheels.backend.request.user.ToggleStatusRequest;
import com.sxhta.cloud.wheels.backend.request.user.UserRequest;
import com.sxhta.cloud.wheels.backend.request.user.UserSearchRequest;
import com.sxhta.cloud.wheels.backend.response.common.ImageUploadResponse;
import com.sxhta.cloud.wheels.backend.response.user.UserResponse;
import org.springframework.web.multipart.MultipartFile;

public interface UserService extends IService<WheelsFrontUser>,
        ICommonService<UserSearchRequest, UserRequest, UserResponse> {

    ImageUploadResponse uploadImage(MultipartFile file);

    Boolean toggleStatus(ToggleStatusRequest request);
}
