package com.sxhta.cloud.wheels.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sxhta.cloud.remote.domain.SysUser;
import com.sxhta.cloud.remote.vo.SystemUserCacheVo;
import com.sxhta.cloud.security.service.TokenService;
import com.sxhta.cloud.wheels.entity.feedback.FeedbackType;
import com.sxhta.cloud.wheels.mapper.FeedbackTypeMapper;
import com.sxhta.cloud.wheels.request.feedback.FeedbackTypeRequest;
import com.sxhta.cloud.wheels.request.feedback.FeedbackTypeSearchRequest;
import com.sxhta.cloud.wheels.response.feedback.FeedbackTypeResponse;
import com.sxhta.cloud.wheels.service.FeedbackTypeService;
import jakarta.inject.Inject;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class FeedbackTypeServiceImpl extends ServiceImpl<FeedbackTypeMapper, FeedbackType> implements FeedbackTypeService {

    @Inject
    private TokenService<SystemUserCacheVo, SysUser> tokenService;


    @Override
    public Boolean create(FeedbackTypeRequest feedbackTypeRequest) {
        final var feedbackType = new FeedbackType();
        BeanUtils.copyProperties(feedbackTypeRequest, feedbackType);
        feedbackType.setCreateBy(tokenService.getUsername());
        return save(feedbackType);
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
        return null;
    }

    @Override
    public Boolean deleteByHash(String hash) {
        return null;
    }

    @Override
    public Boolean updateEntity(FeedbackTypeRequest feedbackTypeRequest) {


        return null;
    }

    @Override
    public List<FeedbackTypeResponse> getAdminList(FeedbackTypeSearchRequest request) {
        final var feedbackTypeResponseList = new ArrayList<FeedbackTypeResponse>();
        final var feedbackTypeLqw = new LambdaQueryWrapper<FeedbackType>();
        //TODO:查询参数
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
        final var feedbackTypeLqw = new LambdaQueryWrapper<FeedbackType>();
        feedbackTypeLqw.eq(FeedbackType::getHash, hash);
        final var feedbackType = getOne(feedbackTypeLqw);
        Boolean status = feedbackType.getStatus();
        if (status) {
            feedbackType.setStatus(false);
        } else {
            feedbackType.setStatus(true);
        }
        return updateById(feedbackType);
    }
}




