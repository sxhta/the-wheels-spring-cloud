package com.sxhta.cloud.wheels.backend.service.feedback.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sxhta.cloud.common.exception.ServiceException;
import com.sxhta.cloud.remote.domain.SysUser;
import com.sxhta.cloud.remote.vo.SystemUserCacheVo;
import com.sxhta.cloud.security.service.TokenService;
import com.sxhta.cloud.wheels.backend.entity.feedback.FeedbackType;
import com.sxhta.cloud.wheels.backend.mapper.feedback.FeedbackTypeMapper;
import com.sxhta.cloud.wheels.backend.request.feedback.FeedbackTypeRequest;
import com.sxhta.cloud.wheels.backend.request.feedback.FeedbackTypeSearchRequest;
import com.sxhta.cloud.wheels.backend.response.feedback.FeedbackTypeResponse;
import com.sxhta.cloud.wheels.backend.service.feedback.FeedbackTypeService;
import jakarta.inject.Inject;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class FeedbackTypeServiceImpl extends ServiceImpl<FeedbackTypeMapper, FeedbackType> implements FeedbackTypeService {

    @Inject
    private TokenService<SystemUserCacheVo, SysUser> tokenService;


    @Override
    public Boolean create(FeedbackTypeRequest feedbackTypeRequest) {
        String name = feedbackTypeRequest.getName();
        final var feedbackTypeLqw = new LambdaQueryWrapper<FeedbackType>();
        feedbackTypeLqw.eq(FeedbackType::getName, name)
                .isNull(FeedbackType::getDeleteTime);
        final var feedbackType = getOne(feedbackTypeLqw);
        if (ObjectUtil.isNotNull(feedbackType)) {
            throw new ServiceException("该反馈类型已存在");
        }
        final var newFeedbackType = new FeedbackType();
        newFeedbackType.setName(name)
                .setStatus(feedbackTypeRequest.getStatus())
                .setCreateBy(tokenService.getUsername());
        return save(newFeedbackType);
    }

    @Override
    public FeedbackTypeResponse getInfoByHash(String hash) {
        final var feedbackTypeLqw = new LambdaQueryWrapper<FeedbackType>();
        feedbackTypeLqw.eq(FeedbackType::getHash, hash);
        final var feedbackType = getOne(feedbackTypeLqw);
        final var feedbackTypeResponse = new FeedbackTypeResponse();
        BeanUtils.copyProperties(feedbackType, feedbackTypeResponse);
        return feedbackTypeResponse;
    }

    @Override
    public Boolean softDeleteByHash(String hash) {
        final var feedbackType = getEntity(hash);
        if (ObjectUtil.isNull(feedbackType)) {
            throw new ServiceException("该反馈类型异常,请联系管理员");
        }
        feedbackType.setUpdateBy(tokenService.getUsername())
                .setDeleteTime(LocalDateTime.now());
        return updateById(feedbackType);
    }

    @Override
    public Boolean deleteByHash(String hash) {
        return null;
    }

    @Override
    public Boolean updateEntity(FeedbackTypeRequest feedbackTypeRequest) {
        final var name = feedbackTypeRequest.getName();
        final var hash = feedbackTypeRequest.getHash();
        final var feedbackTypeLqw = new LambdaQueryWrapper<FeedbackType>();
        feedbackTypeLqw.eq(FeedbackType::getName, name)
                .isNull(FeedbackType::getDeleteTime);
        final var feedbackType = getOne(feedbackTypeLqw);
        if (ObjectUtil.isNotNull(feedbackType)) {
            if (!feedbackType.getHash().equals(feedbackTypeRequest.getHash())) {
                throw new ServiceException("该反馈类型已存在");
            }
        }
        final var oldFeedbackType = getEntity(hash);
        if (ObjectUtil.isNull(oldFeedbackType)) {
            throw new ServiceException("该反馈类型异常，请联系管理员");
        }
        oldFeedbackType.setName(name)
                .setStatus(feedbackTypeRequest.getStatus())
                .setUpdateBy(tokenService.getUsername())
                .setUpdateTime(LocalDateTime.now());
        return updateById(oldFeedbackType);
    }

    @Override
    public List<FeedbackTypeResponse> getAdminList(FeedbackTypeSearchRequest request) {
        final var feedbackTypeResponseList = new ArrayList<FeedbackTypeResponse>();
        final var feedbackTypeLqw = new LambdaQueryWrapper<FeedbackType>();
        //TODO:查询参数
        feedbackTypeLqw.isNull(FeedbackType::getDeleteTime);
        final var feedbackTypeList = list(feedbackTypeLqw);
        if (CollUtil.isNotEmpty(feedbackTypeList)) {
            feedbackTypeList.forEach(feedbackType -> {
                final var feedbackTypeResponse = new FeedbackTypeResponse();
                BeanUtils.copyProperties(feedbackType, feedbackTypeResponse);
                feedbackTypeResponseList.add(feedbackTypeResponse);
            });
        }
        return feedbackTypeResponseList;
    }

    @Override
    public Boolean updateStatus(String hash) {
        final var feedbackType = getEntity(hash);
        if (ObjectUtil.isNull(feedbackType)) {
            throw new ServiceException("该反馈类型异常，请联系管理员");
        }
        Boolean status = feedbackType.getStatus();
        if (status) {
            feedbackType.setStatus(false);
        } else {
            feedbackType.setStatus(true);
        }
        return updateById(feedbackType);
    }

    @Override
    public List<FeedbackTypeResponse> getAll() {
        final var feedbackTypeResponseList = new ArrayList<FeedbackTypeResponse>();
        final var feedbackTypeLqw = new LambdaQueryWrapper<FeedbackType>();
        feedbackTypeLqw.isNull(FeedbackType::getDeleteTime);
        final var feedbackTypeList = list(feedbackTypeLqw);
        if (CollUtil.isNotEmpty(feedbackTypeList)) {
            feedbackTypeList.forEach(feedbackType -> {
                final var feedbackTypeResponse = new FeedbackTypeResponse();
                BeanUtils.copyProperties(feedbackType, feedbackTypeResponse);
                feedbackTypeResponseList.add(feedbackTypeResponse);
            });
        }
        return feedbackTypeResponseList;
    }


    private FeedbackType getEntity(String hash) {
        final var feedbackTypeLqw = new LambdaQueryWrapper<FeedbackType>();
        feedbackTypeLqw.eq(FeedbackType::getHash, hash)
                .isNull(FeedbackType::getDeleteTime);
        return getOne(feedbackTypeLqw);
    }


}




