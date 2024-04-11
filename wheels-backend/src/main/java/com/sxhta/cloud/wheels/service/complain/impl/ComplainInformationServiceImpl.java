package com.sxhta.cloud.wheels.service.complain.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sxhta.cloud.common.exception.ServiceException;
import com.sxhta.cloud.remote.domain.SysUser;
import com.sxhta.cloud.remote.vo.SystemUserCacheVo;
import com.sxhta.cloud.security.service.TokenService;
import com.sxhta.cloud.wheels.entity.complain.ComplainInformation;
import com.sxhta.cloud.wheels.mapper.complain.ComplainInformationMapper;
import com.sxhta.cloud.wheels.request.complain.ComplainInformationRequest;
import com.sxhta.cloud.wheels.request.complain.ComplainInformationSearchRequest;
import com.sxhta.cloud.wheels.response.complain.ComplainInformationResponse;
import com.sxhta.cloud.wheels.service.complain.ComplainInformationService;
import com.sxhta.cloud.wheels.service.complain.ComplainTypeService;
import jakarta.inject.Inject;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static com.sxhta.cloud.common.utils.CharacterConvert.stringToJsonList;

@Service
public class ComplainInformationServiceImpl extends ServiceImpl<ComplainInformationMapper, ComplainInformation> implements ComplainInformationService {

    @Inject
    private TokenService<SystemUserCacheVo, SysUser> tokenService;

    @Inject
    private ComplainTypeService complainTypeService;


    @Override
    public Boolean create(ComplainInformationRequest complainInformationRequest) {
        final var complainInformation = new ComplainInformation();
        BeanUtils.copyProperties(complainInformationRequest, complainInformation);
        complainInformation.setCreateBy(tokenService.getUsername());
        return save(complainInformation);
    }

    @Override
    public ComplainInformationResponse getInfoByHash(String hash) {
        final var complainInformationLqw = new LambdaQueryWrapper<ComplainInformation>();
        complainInformationLqw.eq(ComplainInformation::getHash, hash);
        final var complainInformation = getOne(complainInformationLqw);
        final var complainInformationResponse = new ComplainInformationResponse();
        BeanUtils.copyProperties(complainInformation, complainInformationResponse);
        return complainInformationResponse;
    }

    @Override
    public Boolean softDeleteByHash(String hash) {
        return null;
    }

    @Override
    public Boolean deleteByHash(String hash) {
        return null;
    }

    @Override
    public Boolean updateEntity(ComplainInformationRequest complainInformationRequest) {
        return null;
    }

    @Override
    public List<ComplainInformationResponse> getAdminList(ComplainInformationSearchRequest request) {
        final var isHandle = request.getIsHandle();
        final var complainTypeHash = request.getComplainTypeHash();
        final var complainStartTime = request.getComplainStartTime();
        final var complainEndTime = request.getComplainEndTime();
        final var handleStartTime = request.getHandleStartTime();
        final var handleEndTime = request.getHandleEndTime();
        final var complainInformationResponseList = new ArrayList<ComplainInformationResponse>();
        final var complainInformationLqw = new LambdaQueryWrapper<ComplainInformation>();
        if (ObjectUtil.isNotNull(isHandle)) {
            complainInformationLqw.eq(ComplainInformation::getIsHandle, isHandle);
        }
        if (StrUtil.isNotBlank(complainTypeHash)) {
            complainInformationLqw.eq(ComplainInformation::getComplainTypeHash, complainTypeHash);
        }
        if (ObjectUtil.isNotNull(complainStartTime) && ObjectUtil.isNotNull(complainEndTime)) {
            complainInformationLqw.between(ComplainInformation::getComplainTime, complainStartTime, complainEndTime);
        }
        if (ObjectUtil.isNotNull(handleStartTime) && ObjectUtil.isNotNull(handleEndTime)) {
            complainInformationLqw.between(ComplainInformation::getComplainTime, handleStartTime, handleEndTime);
        }
        complainInformationLqw.isNull(ComplainInformation::getDeleteTime);
        final var complainInformationList = list(complainInformationLqw);
        if (CollUtil.isNotEmpty(complainInformationList)) {
            complainInformationList.forEach(complainInformation -> {
                final var complainInformationResponse = new ComplainInformationResponse();
                BeanUtils.copyProperties(complainInformation, complainInformationResponse);
                complainInformationResponse.setComplainType(complainTypeService.getEntity(complainInformation.getComplainTypeHash()))
                        .setComplainPhotograph(stringToJsonList(complainInformation.getComplainPhotograph()));
                complainInformationResponseList.add(complainInformationResponse);
            });
        }
        return complainInformationResponseList;
    }

    private ComplainInformation getEntity(String hash) {
        final var complainInformationLqw = new LambdaQueryWrapper<ComplainInformation>();
        complainInformationLqw.eq(ComplainInformation::getHash, hash)
                .isNull(ComplainInformation::getDeleteTime);
        return getOne(complainInformationLqw);
    }

    @Override
    public Boolean handleComplainInformation(ComplainInformationRequest complainInformationRequest) {
        final var complainInformation = getEntity(complainInformationRequest.getHash());
        if (ObjectUtil.isNull(complainInformation)) {
            throw new ServiceException("该投诉信息异常，请联系管理员");
        }
        complainInformation.setIsHandle(true)
                .setHandleBy(tokenService.getUsername())
                .setHandleResult(complainInformationRequest.getHandleResult())
                .setHandleRemark(complainInformationRequest.getHandleRemark())
                .setUpdateBy(tokenService.getUsername())
                .setUpdateTime(LocalDateTime.now());
        return updateById(complainInformation);
    }
}




