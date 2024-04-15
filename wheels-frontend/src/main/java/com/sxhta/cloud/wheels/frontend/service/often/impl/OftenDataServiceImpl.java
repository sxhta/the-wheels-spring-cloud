package com.sxhta.cloud.wheels.frontend.service.often.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sxhta.cloud.security.service.TokenService;
import com.sxhta.cloud.wheels.frontend.entity.often.OftenData;
import com.sxhta.cloud.wheels.frontend.mapper.often.OftenDataMapper;
import com.sxhta.cloud.wheels.frontend.request.often.OftenDataRequest;
import com.sxhta.cloud.wheels.frontend.request.often.OftenDataSearchRequest;
import com.sxhta.cloud.wheels.frontend.response.often.OftenDataResponse;
import com.sxhta.cloud.wheels.frontend.service.often.OftenDataService;
import com.sxhta.cloud.wheels.frontend.service.user.FrontUserInfoService;
import com.sxhta.cloud.wheels.remote.domain.user.WheelsFrontUser;
import com.sxhta.cloud.wheels.remote.vo.FrontUserCacheVo;
import jakarta.inject.Inject;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class OftenDataServiceImpl extends ServiceImpl<OftenDataMapper, OftenData> implements OftenDataService, Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Inject
    private TokenService<FrontUserCacheVo, WheelsFrontUser> tokenService;

    @Inject
    private FrontUserInfoService frontUserInfoService;

    @Override
    public Boolean create(OftenDataRequest oftenDataRequest) {
        final var oftenData = new OftenData();
        BeanUtils.copyProperties(oftenDataRequest, oftenData);
        oftenData.setFromUserHash(frontUserInfoService.getCurrentUserByToken().getHash())
                .setCreateBy(frontUserInfoService.getCurrentUserByToken().getHash());
        return save(oftenData);
    }

    @Override
    public OftenDataResponse getInfoByHash(String hash) {
        final var oftenDataLqw = new LambdaQueryWrapper<OftenData>();
        oftenDataLqw.eq(OftenData::getHash, hash).isNull(OftenData::getDeleteTime);
        final var oftenData = getOne(oftenDataLqw);
        OftenDataResponse oftenDataResponse = new OftenDataResponse();
        if (ObjectUtil.isNotNull(oftenData)) {
            BeanUtils.copyProperties(oftenData, oftenDataResponse);
        }
        return oftenDataResponse;
    }

    @Override
    public Boolean softDeleteByHash(String hash) {
        final var oftenDataLqw = new LambdaQueryWrapper<OftenData>();
        oftenDataLqw.eq(OftenData::getHash, hash)
                .isNull(OftenData::getDeleteTime);
        final var oftenData = getOne(oftenDataLqw);
        oftenData.setDeleteTime(LocalDateTime.now());
        return updateById(oftenData);
    }

    @Override
    public Boolean deleteByHash(String hash) {
        return null;
    }

    @Override
    public Boolean updateEntity(OftenDataRequest oftenDataRequest) {
        final var oftenDataLqw = new LambdaQueryWrapper<OftenData>();
        oftenDataLqw.eq(OftenData::getHash, oftenDataRequest.getHash())
                .isNull(OftenData::getDeleteTime);
        final var oftenData = getOne(oftenDataLqw);
        oftenData.setName(oftenDataRequest.getName())
                .setEnglishName(oftenDataRequest.getEnglishName())
                .setGender(oftenDataRequest.getGender())
                .setPhone(oftenDataRequest.getPhone())
                .setAddress(oftenDataRequest.getAddress())
                .setUpdateBy(frontUserInfoService.getUserHashByToken())
                .setUpdateTime(LocalDateTime.now());
        return updateById(oftenData);
    }

    @Override
    public List<OftenDataResponse> getAdminList(OftenDataSearchRequest request) {
        final var oftenDataResponseList = new ArrayList<OftenDataResponse>();
        final var oftenDataLqw = new LambdaQueryWrapper<OftenData>();
        oftenDataLqw.eq(OftenData::getFromUserHash, frontUserInfoService.getCurrentUserByToken().getHash())
                .isNull(OftenData::getDeleteTime);
        final var oftenDataList = list(oftenDataLqw);
        if (CollUtil.isNotEmpty(oftenDataList)) {
            oftenDataList.forEach(oftenData -> {
                final var oftenDataResponse = new OftenDataResponse();
                BeanUtils.copyProperties(oftenData, oftenDataResponse);
                oftenDataResponseList.add(oftenDataResponse);
            });
        }
        return oftenDataResponseList;
    }

    @Override
    public String getCurrentUserHash() {
        String userHash = tokenService.getUserHash();
        if (StrUtil.isNotBlank(userHash)) {
            return userHash;
        }
        return "没有hash";
    }
}
