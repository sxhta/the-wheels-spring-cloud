package com.wheels.cloud.frontend.service.user.impl;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sxhta.cloud.common.exception.CommonNullException;
import com.sxhta.cloud.common.exception.ServiceException;
import com.sxhta.cloud.common.utils.file.FileTypeUtils;
import com.sxhta.cloud.common.utils.file.MimeTypeUtils;
import com.sxhta.cloud.remote.RemoteFileOpenFeign;
import com.sxhta.cloud.security.service.TokenService;
import com.sxhta.cloud.wheels.remote.domain.user.WheelsFrontUser;
import com.sxhta.cloud.wheels.remote.vo.FrontUserCacheVo;
import com.wheels.cloud.frontend.mapper.user.FrontUserMapper;
import com.wheels.cloud.frontend.request.user.AvatarUpdateRequest;
import com.wheels.cloud.frontend.response.user.AvatarResponse;
import com.wheels.cloud.frontend.response.user.FrontUserInfoResponse;
import com.wheels.cloud.frontend.service.user.FrontUserInfoService;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.Serial;
import java.io.Serializable;
import java.util.Arrays;

@Slf4j
@Service
public class FrontUserInfoServiceImpl extends ServiceImpl<FrontUserMapper, WheelsFrontUser>
        implements FrontUserInfoService, Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private final Logger logger = LoggerFactory.getLogger(FrontUserInfoServiceImpl.class);

    @Inject
    private TokenService<FrontUserCacheVo, WheelsFrontUser> tokenService;

    @Inject
    private RemoteFileOpenFeign remoteFileOpenFeign;

    @Override
    public WheelsFrontUser getCurrentUserByToken() {
        final var frontUserCacheVo = tokenService.getLoginUser();
        if (ObjectUtil.isNull(frontUserCacheVo)) {
            logger.error("用户缓存为空");
            throw new CommonNullException("用户缓存为空");
        }
        final var frontUserInCache = frontUserCacheVo.getUserEntity();
        if (ObjectUtil.isNull(frontUserInCache)) {
            logger.error("用户不存在");
            throw new CommonNullException("用户不存在");
        }
        final var userId = frontUserInCache.getUserId();
        final var lqw = new LambdaQueryWrapper<WheelsFrontUser>();
        lqw.eq(WheelsFrontUser::getUserId, userId);
        return getOne(lqw);
    }

    @Override
    public FrontUserInfoResponse getUserInfoByToken() {
        final var currentUser = getCurrentUserByToken();
        final var response = new FrontUserInfoResponse();
        BeanUtils.copyProperties(currentUser, response);
        return response;
    }

    @Override
    public AvatarResponse uploadAvatar(MultipartFile file) {

        final var extension = FileTypeUtils.getExtension(file);
        if (!StringUtils.equalsAnyIgnoreCase(extension, MimeTypeUtils.IMAGE_EXTENSION)) {
            throw new ServiceException("文件格式不正确，请上传" + Arrays.toString(MimeTypeUtils.IMAGE_EXTENSION) + "格式");
        }
        final var remoteResponse = remoteFileOpenFeign.upload(file, "/user/avatar");
        if (ObjectUtil.isNull(remoteResponse)) {
            throw new CommonNullException("远程调用文件响应结果为空");
        }
        final var fileInfo = remoteResponse.getData();
        if (ObjectUtil.isNull(fileInfo)) {
            throw new CommonNullException("远程调用文件结果为空");
        }
        final var url = fileInfo.getUrl();
        final var name = fileInfo.getName();
        final var response = new AvatarResponse();
        response.setImageUrl(url)
                .setName(name);
        return response;
    }

    @Override
    public Boolean updateUserAvatar(AvatarUpdateRequest request) {
        final var avatar = request.getAvatar();
        final var currentUser = getCurrentUserByToken();
        currentUser.setAvatar(avatar);
        return updateById(currentUser);
    }
}
