package com.sxhta.cloud.system.backend.controller;

import cn.hutool.core.util.ObjectUtil;
import com.sxhta.cloud.common.utils.file.FileTypeUtils;
import com.sxhta.cloud.common.utils.file.MimeTypeUtils;
import com.sxhta.cloud.common.web.controller.BaseController;
import com.sxhta.cloud.common.web.domain.CommonResponse;
import com.sxhta.cloud.log.annotation.Log;
import com.sxhta.cloud.log.enums.BusinessType;
import com.sxhta.cloud.remote.RemoteFileOpenFeign;
import com.sxhta.cloud.remote.domain.SysUser;
import com.sxhta.cloud.remote.vo.SystemUserCacheVo;
import com.sxhta.cloud.security.service.SecurityService;
import com.sxhta.cloud.security.service.TokenService;
import com.sxhta.cloud.system.backend.response.AvatarResponse;
import com.sxhta.cloud.system.backend.response.UserProfileResponse;
import com.sxhta.cloud.system.backend.service.ISysUserService;
import jakarta.inject.Inject;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;

/**
 * 个人信息 业务处理
 */
@RestController
@RequestMapping("/user/profile")
public class SysProfileController extends BaseController {
    @Inject
    private ISysUserService userService;

    @Inject
    private TokenService<SystemUserCacheVo, SysUser> tokenService;

    @Inject
    private RemoteFileOpenFeign remoteFileService;

    @Inject
    private SecurityService securityService;


    /**
     * 个人信息
     */
    @GetMapping
    public CommonResponse<UserProfileResponse> profile() {
        final var username = tokenService.getLoginUser().getUsername();
        final var user = userService.selectUserByUserName(username);
        final var response = new UserProfileResponse();
        response.setUser(user)
                .setRoleGroup(userService.selectUserRoleGroup(username))
                .setPostGroup(userService.selectUserPostGroup(username));
        return CommonResponse.success(response);
    }

    /**
     * 修改用户
     */
    @Log(title = "个人信息", businessType = BusinessType.UPDATE)
    @PutMapping
    public CommonResponse<Boolean> updateProfile(@RequestBody SysUser user) {
        final var loginUser = tokenService.getLoginUser();
        final var currentUser = loginUser.getUserEntity();
        currentUser.setNickName(user.getNickName());
        currentUser.setEmail(user.getEmail());
        currentUser.setPhonenumber(user.getPhonenumber());
        currentUser.setSex(user.getSex());
        if (ObjectUtil.isNotEmpty(user.getPhonenumber()) && !userService.checkPhoneUnique(currentUser)) {
            return CommonResponse.error("修改用户'" + user.getUserName() + "'失败，手机号码已存在");
        }
        if (ObjectUtil.isNotEmpty(user.getEmail()) && !userService.checkEmailUnique(currentUser)) {
            return CommonResponse.error("修改用户'" + user.getUserName() + "'失败，邮箱账号已存在");
        }
        if (userService.updateUserProfile(currentUser)) {
            // 更新缓存用户信息
            tokenService.setLoginUser(loginUser);
            return CommonResponse.success();
        }
        return CommonResponse.error("修改个人信息异常，请联系管理员");
    }

    /**
     * 重置密码
     */
    @Log(title = "个人信息", businessType = BusinessType.UPDATE)
    @PutMapping("/updatePwd")
    public CommonResponse<Boolean> updatePwd(String oldPassword, String newPassword) {
        final var username = tokenService.getLoginUser().getUsername();
        final var user = userService.selectUserByUserName(username);
        final var password = user.getPassword();
        if (!securityService.matchesPassword(oldPassword, password)) {
            return CommonResponse.error("修改密码失败，旧密码错误");
        }
        if (securityService.matchesPassword(newPassword, password)) {
            return CommonResponse.error("新密码不能与旧密码相同");
        }
        if (userService.resetUserPwd(username, securityService.encryptPassword(newPassword))) {
            // 更新缓存用户密码
            final var loginUser = tokenService.getLoginUser();
            loginUser.getUserEntity().setPassword(securityService.encryptPassword(newPassword));
            tokenService.setLoginUser(loginUser);
            return CommonResponse.success();
        }
        return CommonResponse.error("修改密码异常，请联系管理员");
    }

    /**
     * 头像上传
     */
    @Log(title = "用户头像", businessType = BusinessType.UPDATE)
    @PostMapping("/avatar")
    public CommonResponse<AvatarResponse> avatar(@RequestParam("avatarfile") MultipartFile file) {
        if (!file.isEmpty()) {
            final var loginUser = tokenService.getLoginUser();
            final var extension = FileTypeUtils.getExtension(file);
            if (!StringUtils.equalsAnyIgnoreCase(extension, MimeTypeUtils.IMAGE_EXTENSION)) {
                return CommonResponse.error("文件格式不正确，请上传" + Arrays.toString(MimeTypeUtils.IMAGE_EXTENSION) + "格式");
            }
            final var fileResult = remoteFileService.upload(file);
            if (ObjectUtil.isNull(fileResult) || ObjectUtil.isNull(fileResult.getData())) {
                return CommonResponse.error("文件服务异常，请联系管理员");
            }
            final var url = fileResult.getData().getUrl();
            if (userService.updateUserAvatar(loginUser.getUsername(), url)) {
                // 更新缓存用户头像
                loginUser.getUserEntity().setAvatar(url);
                tokenService.setLoginUser(loginUser);
                final var response = new AvatarResponse();
                response.setImgUrl(url);
                return CommonResponse.success(response);
            }
        }
        return CommonResponse.error("上传图片异常，请联系管理员");
    }
}
