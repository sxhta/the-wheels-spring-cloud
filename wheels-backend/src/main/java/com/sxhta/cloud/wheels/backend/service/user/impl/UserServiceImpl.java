package com.sxhta.cloud.wheels.backend.service.user.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sxhta.cloud.common.exception.CommonNullException;
import com.sxhta.cloud.common.exception.ServiceException;
import com.sxhta.cloud.common.utils.encrypt.EncryptUtil;
import com.sxhta.cloud.common.utils.file.FileTypeUtils;
import com.sxhta.cloud.common.utils.file.MimeTypeUtils;
import com.sxhta.cloud.common.utils.uuid.UUID;
import com.sxhta.cloud.remote.RemoteFileOpenFeign;
import com.sxhta.cloud.remote.domain.SysUser;
import com.sxhta.cloud.remote.service.AttachmentService;
import com.sxhta.cloud.remote.vo.SystemUserCacheVo;
import com.sxhta.cloud.security.service.TokenService;
import com.sxhta.cloud.wheels.backend.mapper.user.UserMapper;
import com.sxhta.cloud.wheels.backend.request.user.ToggleStatusRequest;
import com.sxhta.cloud.wheels.backend.request.user.UserRequest;
import com.sxhta.cloud.wheels.backend.request.user.UserSearchRequest;
import com.sxhta.cloud.wheels.backend.response.common.ImageUploadResponse;
import com.sxhta.cloud.wheels.backend.response.user.UserResponse;
import com.sxhta.cloud.wheels.backend.service.user.UserService;
import com.sxhta.cloud.wheels.remote.domain.user.WheelsFrontUser;
import jakarta.inject.Inject;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, WheelsFrontUser> implements UserService {

    @Inject
    private TokenService<SystemUserCacheVo, SysUser> tokenService;

    @Inject
    private RemoteFileOpenFeign remoteFileOpenFeign;

    @Inject
    private AttachmentService attachmentService;

    @Override
    public Boolean create(UserRequest request) {
        final var entity = new WheelsFrontUser();
        BeanUtils.copyProperties(request, entity);
        entity.setCreateBy(tokenService.getLoginUser().getUsername());
        final var hash = EncryptUtil.generateEntityHash(UUID.randomUUID(true).toString());
        entity.setHash(hash);
        final var avatar = entity.getAvatar();
        final var resultAvatar = attachmentService.clearPrefix(avatar);
        entity.setAvatar(resultAvatar);
        return save(entity);
    }

    @Override
    public UserResponse getInfoByHash(String hash) {
        final var lqw = new LambdaQueryWrapper<WheelsFrontUser>();
        lqw.eq(WheelsFrontUser::getHash, hash);
        final var entity = getOne(lqw);
        if (ObjectUtil.isNull(entity)) {
            throw new CommonNullException("该用户不存在");
        }
        final var response = new UserResponse();
        BeanUtils.copyProperties(entity, response);
        return response;
    }

    @Override
    public Boolean softDeleteByHash(String hash) {
        final var lqw = new LambdaQueryWrapper<WheelsFrontUser>();
        lqw.eq(WheelsFrontUser::getHash, hash);
        final var entity = getOne(lqw);
        if (ObjectUtil.isNull(entity)) {
            throw new CommonNullException("该用户不存在");
        }
        entity.setDeleteTime(LocalDateTime.now());
        return updateById(entity);
    }

    @Override
    public Boolean deleteByHash(String hash) {
        final var lqw = new LambdaQueryWrapper<WheelsFrontUser>();
        lqw.eq(WheelsFrontUser::getHash, hash);
        final var entity = getOne(lqw);
        if (ObjectUtil.isNull(entity)) {
            throw new CommonNullException("该用户不存在");
        }
        return removeById(entity);
    }

    @Override
    public Boolean updateEntity(UserRequest request) {
        final var hash = request.getHash();
        final var lqw = new LambdaQueryWrapper<WheelsFrontUser>();
        lqw.eq(WheelsFrontUser::getHash, hash);
        final var entity = getOne(lqw);
        if (ObjectUtil.isNull(entity)) {
            throw new CommonNullException("该用户不存在");
        }
        BeanUtils.copyProperties(request, entity);
        final var avatar = entity.getAvatar();
        final var resultAvatar = attachmentService.clearPrefix(avatar);
        entity.setAvatar(resultAvatar);
        return updateById(entity);
    }

    @Override
    public List<UserResponse> getAdminList(UserSearchRequest request) {
        final var lqw = new LambdaQueryWrapper<WheelsFrontUser>();
        lqw.isNull(WheelsFrontUser::getDeleteTime);
        final var searchAccount = request.getAccount();
        if (StrUtil.isNotBlank(searchAccount)) {
            lqw.and(consumer -> consumer.eq(WheelsFrontUser::getAccount, searchAccount));
        }
        final var entityList = list(lqw);
        final var responseList = new ArrayList<UserResponse>();
        entityList.forEach(entity -> {
            final var response = new UserResponse();
            BeanUtils.copyProperties(entity, response);
            final var avatar = response.getAvatar();
            final var resultAvatar = attachmentService.addPrefix(avatar);
            response.setAvatar(resultAvatar);
            final var status = entity.getStatus();
            final var statusInt = Integer.valueOf(status);
            response.setStatus(statusInt);
            if (statusInt.equals(0)) {
                response.setIsEnable(true);
                response.setStatusName("正常");
            } else {
                response.setIsEnable(false);
                response.setStatusName("禁用");
            }
            responseList.add(response);
        });
        return responseList;
    }

    @Override
    public ImageUploadResponse uploadImage(MultipartFile file) {
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
        final var domain = attachmentService.getDomain();
        final var response = new ImageUploadResponse();
        response.setImageUrl(url)
                .setDomain(domain)
                .setName(name);
        final var resultUrl = domain + url;
        response.setImageUrl(resultUrl);
        return response;
    }

    @Override
    public Boolean toggleStatus(ToggleStatusRequest request) {
        final var hash = request.getHash();
        final var lqw = new LambdaQueryWrapper<WheelsFrontUser>();
        lqw.eq(WheelsFrontUser::getHash, hash);
        final var currentUser = getOne(lqw);
        if (ObjectUtil.isNull(currentUser)) {
            throw new CommonNullException("该用户不存在");
        }
        final var currentStatus = currentUser.getStatus();
        if (currentStatus.equals("0")) {
            currentUser.setStatus("1");
        }
        if (currentStatus.equals("1")) {
            currentUser.setStatus("0");
        }
        return updateById(currentUser);
    }

    @Override
    public WheelsFrontUser getUserByHash(String hash) {
        final var wheelsFrontUserLqw = new LambdaQueryWrapper<WheelsFrontUser>();
        wheelsFrontUserLqw.eq(WheelsFrontUser::getHash, hash)
                .isNull(WheelsFrontUser::getDeleteTime);
        return getOne(wheelsFrontUserLqw);
    }

    @Override
    public List<String> getUserHashListByName(String name) {
        final var userHashList = new ArrayList<String>();
        final var wheelsFrontUserLqw = new LambdaQueryWrapper<WheelsFrontUser>();
        wheelsFrontUserLqw.like(WheelsFrontUser::getNickname, name).isNull(WheelsFrontUser::getDeleteTime);
        final var wheelsFrontUserList = list(wheelsFrontUserLqw);
        if (CollUtil.isNotEmpty(wheelsFrontUserList)) {
            wheelsFrontUserList.forEach(wheelsFrontUser -> userHashList.add(wheelsFrontUser.getHash()));
        }
        return userHashList;
    }

    @Override
    public List<WheelsFrontUser> getAdminEntityList() {
        final var lqw = new LambdaQueryWrapper<WheelsFrontUser>();
        lqw.isNull(WheelsFrontUser::getDeleteTime);
        final var entityList = list(lqw);
        return entityList;
    }
}
