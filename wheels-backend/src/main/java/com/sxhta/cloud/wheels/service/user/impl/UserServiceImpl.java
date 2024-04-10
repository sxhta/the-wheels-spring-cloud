package com.sxhta.cloud.wheels.service.user.impl;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sxhta.cloud.common.exception.CommonNullException;
import com.sxhta.cloud.common.utils.encrypt.EncryptUtil;
import com.sxhta.cloud.common.utils.uuid.UUID;
import com.sxhta.cloud.remote.domain.SysUser;
import com.sxhta.cloud.remote.vo.SystemUserCacheVo;
import com.sxhta.cloud.security.service.TokenService;
import com.sxhta.cloud.wheels.mapper.user.UserMapper;
import com.sxhta.cloud.wheels.remote.domain.user.WheelsFrontUser;
import com.sxhta.cloud.wheels.request.user.UserRequest;
import com.sxhta.cloud.wheels.request.user.UserSearchRequest;
import com.sxhta.cloud.wheels.response.user.UserResponse;
import com.sxhta.cloud.wheels.service.user.UserService;
import jakarta.inject.Inject;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, WheelsFrontUser>
        implements UserService {

    @Inject
    private TokenService<SystemUserCacheVo, SysUser> tokenService;

    @Override
    public Boolean create(UserRequest request) {
        final var entity = new WheelsFrontUser();
        BeanUtils.copyProperties(request, entity);
        entity.setCreateBy(tokenService.getLoginUser().getUsername());
        final var hash = EncryptUtil.generateEntityHash(UUID.randomUUID(true).toString());
        entity.setHash(hash);
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
            throw new CommonNullException("该文章不存在");
        }
        BeanUtils.copyProperties(request, entity);
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
            responseList.add(response);
        });
        return responseList;
    }
}
