package com.sxhta.cloud.wheels.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sxhta.cloud.common.exception.ServiceException;
import com.sxhta.cloud.remote.domain.SysUser;
import com.sxhta.cloud.remote.vo.SystemUserCacheVo;
import com.sxhta.cloud.security.service.TokenService;
import com.sxhta.cloud.wheels.entity.RealNameOwner;
import com.sxhta.cloud.wheels.mapper.RealNameOwnerMapper;
import com.sxhta.cloud.wheels.request.RealNameOwnerRequest;
import com.sxhta.cloud.wheels.request.RealNameOwnerSearchRequest;
import com.sxhta.cloud.wheels.response.RealNameOwnerResponse;
import com.sxhta.cloud.wheels.service.RealNameOwnerService;
import jakarta.inject.Inject;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 【wheels_real_name_owner(车主实名认证)】的数据库操作Service实现
 */
@Service
public class RealNameOwnerServiceImpl extends ServiceImpl<RealNameOwnerMapper, RealNameOwner> implements RealNameOwnerService {

    @Inject
    private TokenService<SystemUserCacheVo, SysUser> tokenService;

    @Override
    public Boolean create(RealNameOwnerRequest realNameOwnerRequest) {
        final var realNameOwner = new RealNameOwner();
        BeanUtils.copyProperties(realNameOwnerRequest, realNameOwner);
        realNameOwner.setCreateBy(tokenService.getLoginUser().getUsername());
        return save(realNameOwner);
    }

    @Override
    public RealNameOwnerResponse getInfoByHash(String hash) {
        final var realNameOwnerLqw = new LambdaQueryWrapper<RealNameOwner>();
        realNameOwnerLqw.eq(RealNameOwner::getHash, hash);
        final var realNameOwner = getOne(realNameOwnerLqw);
        if (ObjectUtil.isNull(realNameOwner)) {
            throw new ServiceException("该数据异常，请联系管理员");
        }
        final var realNameOwnerResponse = new RealNameOwnerResponse();
        BeanUtils.copyProperties(realNameOwner, realNameOwnerResponse);
        return realNameOwnerResponse;
    }

    @Override
    public Boolean softDeleteByHash(String hash) {
        return null;
    }

    @Override
    public Boolean deleteByHash(String hash) {
        final var realNameOwnerLqw = new LambdaQueryWrapper<RealNameOwner>();
        realNameOwnerLqw.eq(RealNameOwner::getHash, hash);
        final var realNameOwner = getOne(realNameOwnerLqw);
        if (ObjectUtil.isNull(realNameOwner)) {
            throw new ServiceException("该数据异常,请联系管理员");
        }
        return removeById(realNameOwner);
    }

    @Override
    public Boolean updateEntity(RealNameOwnerRequest realNameOwnerRequest) {
        final var realNameOwner = new RealNameOwner();
        BeanUtils.copyProperties(realNameOwnerRequest, realNameOwner);
        realNameOwner.setCreateBy(tokenService.getLoginUser().getUsername());
        return updateById(realNameOwner);
    }


    @Override
    public List<RealNameOwnerResponse> getAdminList(RealNameOwnerSearchRequest request) {
        final var realNameOwnerLqw = new LambdaQueryWrapper<RealNameOwner>();
        final var realNameOwnerList = list(realNameOwnerLqw);
        final var realNameOwnerResponseList = new ArrayList<RealNameOwnerResponse>();
        realNameOwnerList.forEach(realNameOwner -> {
            final var realNameOwnerResponse = new RealNameOwnerResponse();
            BeanUtils.copyProperties(realNameOwner, realNameOwnerResponse);
            realNameOwnerResponseList.add(realNameOwnerResponse);
        });
        return realNameOwnerResponseList;
    }

    @Override
    public String listToJsonString(List<String> list) {
        return RealNameOwnerService.super.listToJsonString(list);
    }
}




