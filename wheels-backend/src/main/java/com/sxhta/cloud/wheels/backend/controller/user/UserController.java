package com.sxhta.cloud.wheels.backend.controller.user;

import com.sxhta.cloud.common.web.controller.BaseController;
import com.sxhta.cloud.common.web.controller.ICommonController;
import com.sxhta.cloud.common.web.domain.CommonResponse;
import com.sxhta.cloud.common.web.page.PageRequest;
import com.sxhta.cloud.common.web.page.TableDataInfo;
import com.sxhta.cloud.wheels.backend.request.user.ToggleStatusRequest;
import com.sxhta.cloud.wheels.backend.request.user.UserRequest;
import com.sxhta.cloud.wheels.backend.request.user.UserSearchRequest;
import com.sxhta.cloud.wheels.backend.response.common.ImageUploadResponse;
import com.sxhta.cloud.wheels.backend.response.user.UserResponse;
import com.sxhta.cloud.wheels.backend.service.user.UserService;
import com.sxhta.cloud.wheels.remote.domain.user.WheelsFrontUser;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@RestController
@RequestMapping("/user")
@Tag(name = "用户管理", description = "用户管理控制器")
public class UserController extends BaseController implements
        ICommonController<UserSearchRequest, UserRequest, UserResponse> {

    @Inject
    private UserService userService;

    @Override
    @Operation(summary = "用户列表")
    @GetMapping("/list1")
    public TableDataInfo<UserResponse> getAdminList(UserSearchRequest request, PageRequest pageRequest) {
        startPage(pageRequest);
        final var list = userService.getAdminList(request);
        return CommonResponse.list(list);
    }

    @Operation(summary = "用户列表")
    @GetMapping("/list")
    public TableDataInfo<WheelsFrontUser> getAdminList1(UserSearchRequest request, PageRequest pageRequest) {
        startPage(pageRequest);
        final var list = userService.getAdminEntityList();
        return CommonResponse.list(list);
    }

    @Override
    @Operation(summary = "用户详情")
    @GetMapping("/info")
    public CommonResponse<UserResponse> getInfoByHash(String hash) {
        final var result = userService.getInfoByHash(hash);
        return CommonResponse.success(result);
    }

    @Override
    @Operation(summary = "创建用户")
    @PostMapping("/create")
    public CommonResponse<Boolean> create(@RequestBody UserRequest request) {
        final var result = userService.create(request);
        return CommonResponse.result(result);
    }

    @Override
    @Operation(summary = "用户软删除")
    @DeleteMapping("/delete/soft")
    public CommonResponse<Boolean> softDeleteByHash(String hash) {
        final var result = userService.softDeleteByHash(hash);
        return CommonResponse.result(result);
    }

    @Override
    @Operation(summary = "删除用户")
    @DeleteMapping("/delete/hard")
    public CommonResponse<Boolean> deleteByHash(String hash) {
        final var result = userService.deleteByHash(hash);
        return CommonResponse.result(result);
    }

    @Override
    @Operation(summary = "更新用户")
    @PutMapping(value = "/update")
    public CommonResponse<Boolean> updateEntity(@RequestBody UserRequest request) {
        final var result = userService.updateEntity(request);
        return CommonResponse.result(result);
    }

    @Operation(summary = "上传头像")
    @PostMapping(value = "/avatar/upload")
    public CommonResponse<ImageUploadResponse> uploadAvatar(@RequestParam(value = "file") MultipartFile file) {
        final var result = userService.uploadImage(file);
        return CommonResponse.success(result);
    }


    @Operation(summary = "上传头像")
    @PutMapping(value = "/status/toggle")
    public CommonResponse<Boolean> uploadAvatar(@RequestBody ToggleStatusRequest request) {
        final var result = userService.toggleStatus(request);
        return CommonResponse.result(result);
    }
}