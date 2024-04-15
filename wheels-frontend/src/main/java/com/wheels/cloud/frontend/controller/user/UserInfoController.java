package com.wheels.cloud.frontend.controller.user;

import com.sxhta.cloud.common.web.domain.CommonResponse;
import com.wheels.cloud.frontend.request.user.AvatarUpdateRequest;
import com.wheels.cloud.frontend.request.user.UserInfoRequest;
import com.wheels.cloud.frontend.response.user.AvatarResponse;
import com.wheels.cloud.frontend.response.user.FrontUserInfoResponse;
import com.wheels.cloud.frontend.service.user.FrontUserInfoService;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.Serial;
import java.io.Serializable;

@Slf4j
@RestController
@RequestMapping("/user")
public class UserInfoController implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Inject
    private FrontUserInfoService frontUserInfoService;

    @GetMapping("/info")
    public CommonResponse<FrontUserInfoResponse> getUserInfo() {
        final var info = frontUserInfoService.getUserInfoByToken();
        return CommonResponse.success(info);
    }

    @PostMapping("/upload/avatar")
    public CommonResponse<AvatarResponse> uploadAvatar(@RequestParam(value = "file") MultipartFile file) {
        final var response = frontUserInfoService.uploadAvatar(file);
        return CommonResponse.success(response);
    }

    @PutMapping("/update/avatar")
    public CommonResponse<Boolean> updateUserAvatar(@RequestBody AvatarUpdateRequest request) {
        final var response = frontUserInfoService.updateUserAvatar(request);
        return CommonResponse.result(response, "用户头像更新成功");
    }

    @PutMapping("/update/info")
    public CommonResponse<Boolean> updateUserInfo(@RequestBody UserInfoRequest request) {
        final var response = frontUserInfoService.updateUserInfo(request);
        return CommonResponse.result(response, "用户信息更新成功");
    }
}