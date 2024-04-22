package com.sxhta.cloud.wheels.backend.service.user;

import com.baomidou.mybatisplus.extension.service.IService;
import com.sxhta.cloud.common.service.ICommonService;
import com.sxhta.cloud.wheels.backend.request.user.ToggleStatusRequest;
import com.sxhta.cloud.wheels.backend.request.user.UserRequest;
import com.sxhta.cloud.wheels.backend.request.user.UserSearchRequest;
import com.sxhta.cloud.wheels.backend.response.common.ImageUploadResponse;
import com.sxhta.cloud.wheels.backend.response.user.UserResponse;
import com.sxhta.cloud.wheels.remote.domain.user.WheelsFrontUser;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface UserService extends IService<WheelsFrontUser>, ICommonService<UserSearchRequest, UserRequest, UserResponse> {

    ImageUploadResponse uploadImage(MultipartFile file);

    Boolean toggleStatus(ToggleStatusRequest request);


    WheelsFrontUser getUserByHash(String hash);

    List<String> getUserHashListByName(String name);

    List<WheelsFrontUser> getAdminEntityList();


}
