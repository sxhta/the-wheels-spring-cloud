package com.sxhta.cloud.wheels.backend.service.feedback.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sxhta.cloud.common.exception.ServiceException;
import com.sxhta.cloud.remote.domain.SysUser;
import com.sxhta.cloud.remote.vo.SystemUserCacheVo;
import com.sxhta.cloud.security.service.TokenService;
import com.sxhta.cloud.wheels.backend.entity.feedback.FeedbackInformation;
import com.sxhta.cloud.wheels.backend.mapper.feedback.FeedbackInformationMapper;
import com.sxhta.cloud.wheels.backend.request.feedback.FeedbackInformationRequest;
import com.sxhta.cloud.wheels.backend.request.feedback.FeedbackInformationSearchRequest;
import com.sxhta.cloud.wheels.backend.response.feedback.FeedbackInformationResponse;
import com.sxhta.cloud.wheels.backend.service.feedback.FeedbackInformationService;
import com.sxhta.cloud.wheels.cloud.logic.service.user.WheelsUserService;
import jakarta.inject.Inject;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static com.sxhta.cloud.common.utils.CharacterConvert.stringToJsonList;

@Service
public class FeedbackInformationServiceImpl extends ServiceImpl<FeedbackInformationMapper, FeedbackInformation> implements FeedbackInformationService {

    @Inject
    private TokenService<SystemUserCacheVo, SysUser> tokenService;

    @Inject
    private WheelsUserService wheelsUserService;

    @Override
    public Boolean create(FeedbackInformationRequest feedbackInformationRequest) {
        final var feedbackInformation = new FeedbackInformation();
        BeanUtils.copyProperties(feedbackInformationRequest, feedbackInformation);
        feedbackInformation.setFeedbackUser(tokenService.getUsername())
                .setFeedbackPhotograph(listToJsonString(feedbackInformationRequest.getFeedbackPhotograph()))
                .setCreateBy(tokenService.getUsername());
        return save(feedbackInformation);
    }

    @Override
    public FeedbackInformationResponse getInfoByHash(String hash) {
        final var feedbackInformation = getEntity(hash);
        final var feedbackInformationResponse = new FeedbackInformationResponse();
        BeanUtils.copyProperties(feedbackInformation, feedbackInformationResponse);
        final var user = wheelsUserService.getInfoByHash(feedbackInformation.getFeedbackUser());
        feedbackInformationResponse.setFeedbackUser(user.getUserName());
        return feedbackInformationResponse;
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
    public Boolean updateEntity(FeedbackInformationRequest feedbackInformationRequest) {
        final var feedbackInformation = new FeedbackInformation();
        BeanUtils.copyProperties(feedbackInformationRequest, feedbackInformation);
        return updateById(feedbackInformation);
    }

    @Override
    public List<FeedbackInformationResponse> getAdminList(FeedbackInformationSearchRequest request) {
        final var feedbackUser = request.getFeedbackUser();
        final var feedbackTypeHash = request.getFeedbackTypeHash();
        final var isHandle = request.getIsHandle();
        final var feedbackStartTime = request.getFeedbackStartTime();
        final var feedbackEndTime = request.getFeedbackEndTime();
        final var handleStartTime = request.getHandleStartTime();
        final var handleEndTime = request.getHandleEndTime();
        final var feedbackInformationResponseList = new ArrayList<FeedbackInformationResponse>();
        final var feedbackInformationLqw = new LambdaQueryWrapper<FeedbackInformation>();
        if (StrUtil.isNotBlank(feedbackTypeHash)) {
            feedbackInformationLqw.eq(FeedbackInformation::getFeedbackTypeHash, feedbackTypeHash);
        }
        if (ObjectUtil.isNotNull(isHandle)) {
            feedbackInformationLqw.eq(FeedbackInformation::getIsHandle, isHandle);
        }
        if (ObjectUtil.isNotNull(feedbackStartTime) && ObjectUtil.isNotNull(feedbackEndTime)) {
            feedbackInformationLqw.between(FeedbackInformation::getFeedbackTime, feedbackStartTime, feedbackEndTime);
        }
        if (ObjectUtil.isNotNull(handleStartTime) && ObjectUtil.isNotNull(handleEndTime)) {
            feedbackInformationLqw.between(FeedbackInformation::getHandleTime, handleStartTime, handleEndTime);
        }
        if (StrUtil.isNotBlank(feedbackUser)) {
            feedbackInformationLqw.in(FeedbackInformation::getFeedbackUser, wheelsUserService.getHashListByUserNameLike(feedbackUser));
        }
        feedbackInformationLqw.isNull(FeedbackInformation::getDeleteTime);
        final var feedbackInformationList = list(feedbackInformationLqw);
        if (CollUtil.isNotEmpty(feedbackInformationList)) {
            feedbackInformationList.forEach(feedbackInformation -> {
                final var feedbackInformationResponse = new FeedbackInformationResponse();
                BeanUtils.copyProperties(feedbackInformation, feedbackInformationResponse);
                feedbackInformationResponse.setFeedbackPhotograph(stringToJsonList(feedbackInformation.getFeedbackPhotograph()));
                feedbackInformationResponseList.add(feedbackInformationResponse);
            });
        }
        return feedbackInformationResponseList;
    }

    private FeedbackInformation getEntity(String hash) {
        final var feedbackInformationLqw = new LambdaQueryWrapper<FeedbackInformation>();
        feedbackInformationLqw.eq(FeedbackInformation::getHash, hash);
        final var feedbackInformation = getOne(feedbackInformationLqw);
        if (ObjectUtil.isNull(feedbackInformation)) {
            throw new ServiceException("该反馈信息异常，请联系管理员");
        }
        return feedbackInformation;
    }

    @Override
    public Boolean handleFeedbackInformation(FeedbackInformationRequest feedbackInformationRequest) {
        FeedbackInformation feedbackInformation = getEntity(feedbackInformationRequest.getHash());
        if (feedbackInformation.getIsHandle()) {
            throw new ServiceException("该反馈已处理");
        }
        feedbackInformation.setIsHandle(true)
                .setHandleBy(tokenService.getUsername())
                .setHandleTime(LocalDateTime.now())
                .setHandleResult(feedbackInformationRequest.getHandleResult())
                .setHandleRemark(feedbackInformationRequest.getHandleRemark())
                .setUpdateBy(tokenService.getUsername())
                .setUpdateTime(LocalDateTime.now());
        return updateById(feedbackInformation);
    }
}




