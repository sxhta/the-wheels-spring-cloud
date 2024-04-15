package com.sxhta.cloud.wheels.backend.service.complain.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sxhta.cloud.common.exception.ServiceException;
import com.sxhta.cloud.remote.domain.SysUser;
import com.sxhta.cloud.remote.vo.SystemUserCacheVo;
import com.sxhta.cloud.security.service.TokenService;
import com.sxhta.cloud.wheels.backend.entity.complain.ComplainType;
import com.sxhta.cloud.wheels.backend.mapper.complain.ComplainTypeMapper;
import com.sxhta.cloud.wheels.backend.request.complain.ComplainTypeRequest;
import com.sxhta.cloud.wheels.backend.request.complain.ComplainTypeSearchRequest;
import com.sxhta.cloud.wheels.backend.response.complain.ComplainTypeResponse;
import com.sxhta.cloud.wheels.backend.service.complain.ComplainTypeService;
import jakarta.inject.Inject;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class ComplainTypeServiceImpl extends ServiceImpl<ComplainTypeMapper, ComplainType> implements ComplainTypeService {

    @Inject
    private TokenService<SystemUserCacheVo, SysUser> tokenService;


    @Override
    public Boolean create(ComplainTypeRequest complainTypeRequest) {
        final var complainType = new ComplainType();
        BeanUtils.copyProperties(complainTypeRequest, complainType);
        complainType.setCreateBy(tokenService.getUsername());
        return save(complainType);
    }

    @Override
    public ComplainTypeResponse getInfoByHash(String hash) {
        final var complainType = getEntity(hash);
        if (ObjectUtil.isNull(complainType)) {
            throw new ServiceException("该投诉类型异常，请联系管理员");
        }
        final var complainTypeResponse = new ComplainTypeResponse();
        BeanUtils.copyProperties(complainType, complainTypeResponse);
        return complainTypeResponse;
    }

    @Override
    public Boolean softDeleteByHash(String hash) {
        final var complainType = getEntity(hash);
        if (ObjectUtil.isNull(complainType)) {
            throw new ServiceException("该投诉类型异常，请联系管理员");
        }
        complainType.setUpdateBy(tokenService.getUsername())
                .setUpdateTime(LocalDateTime.now())
                .setDeleteTime(LocalDateTime.now());
        return updateById(complainType);
    }

    @Override
    public Boolean deleteByHash(String hash) {
        return null;
    }

    @Override
    public Boolean updateEntity(ComplainTypeRequest complainTypeRequest) {
        final var hash = complainTypeRequest.getHash();
        final var name = complainTypeRequest.getName();
        final var oldComplainType = getEntity(hash);
        if (ObjectUtil.isNull(oldComplainType)) {
            throw new ServiceException("该投诉类型异常，请联系管理员");
        }
        if (!oldComplainType.getName().equals(complainTypeRequest.getName())) {
            final var complainTypeLqw = new LambdaQueryWrapper<ComplainType>();
            complainTypeLqw.eq(ComplainType::getName, name)
                    .isNull(ComplainType::getDeleteTime);
            final var complainTypeList = list(complainTypeLqw);
            if (CollUtil.isNotEmpty(complainTypeList)) {
                throw new ServiceException("该类型已存在");
            }
        }
        oldComplainType.setName(complainTypeRequest.getName())
                .setStatus(complainTypeRequest.getStatus())
                .setUpdateBy(tokenService.getUsername())
                .setUpdateTime(LocalDateTime.now());
        return updateById(oldComplainType);
    }

    @Override
    public List<ComplainTypeResponse> getAdminList(ComplainTypeSearchRequest request) {
        final var name = request.getName();
        final var status = request.getStatus();
        final var complainTypeResponseList = new ArrayList<ComplainTypeResponse>();
        final var complainTypeLqw = new LambdaQueryWrapper<ComplainType>();
        if (StrUtil.isNotBlank(name)) {
            complainTypeLqw.like(ComplainType::getName, name);
        }
        if (ObjectUtil.isNotNull(status)) {
            complainTypeLqw.eq(ComplainType::getStatus, status);
        }
        complainTypeLqw.isNull(ComplainType::getDeleteTime);
        final var complainTypeList = list(complainTypeLqw);
        if (CollUtil.isNotEmpty(complainTypeList)) {
            complainTypeList.forEach(complainType -> {
                final var complainTypeResponse = new ComplainTypeResponse();
                BeanUtils.copyProperties(complainType, complainTypeResponse);
                complainTypeResponseList.add(complainTypeResponse);
            });
        }
        return complainTypeResponseList;
    }


    public ComplainType getEntity(String hash) {
        final var complainTypeLqw = new LambdaQueryWrapper<ComplainType>();
        complainTypeLqw.eq(ComplainType::getHash, hash)
                .isNull(ComplainType::getDeleteTime);
        return getOne(complainTypeLqw);
    }


    @Override
    public Boolean updateStatus(String hash) {
//        final var complainType = getEntity(hash);
        final var complainTypeLqw = new LambdaQueryWrapper<ComplainType>();
        complainTypeLqw.eq(ComplainType::getHash, hash)
                .isNull(ComplainType::getDeleteTime);
        final var complainType = getOne(complainTypeLqw);
        if (ObjectUtil.isNull(complainType)) {
            throw new ServiceException("该投诉类型异常，请联系管理员");
        }
        if (complainType.getStatus()) {
            complainType.setStatus(false);
        } else {
            complainType.setStatus(true);
        }
        return updateById(complainType);
    }


}




