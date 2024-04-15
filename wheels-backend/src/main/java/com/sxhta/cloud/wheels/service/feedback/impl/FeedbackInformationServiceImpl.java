package com.sxhta.cloud.wheels.service.feedback.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sxhta.cloud.common.exception.ServiceException;
import com.sxhta.cloud.remote.domain.SysUser;
import com.sxhta.cloud.remote.vo.SystemUserCacheVo;
import com.sxhta.cloud.security.service.TokenService;
import com.sxhta.cloud.wheels.entity.feedback.FeedbackInformation;
import com.sxhta.cloud.wheels.mapper.feedback.FeedbackInformationMapper;
import com.sxhta.cloud.wheels.request.feedback.FeedbackInformationRequest;
import com.sxhta.cloud.wheels.request.feedback.FeedbackInformationSearchRequest;
import com.sxhta.cloud.wheels.response.feedback.FeedbackInformationResponse;
import com.sxhta.cloud.wheels.response.user.FrontendUserResponse;
import com.sxhta.cloud.wheels.service.feedback.FeedbackInformationService;
import com.sxhta.cloud.wheels.service.user.UserService;
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
    private UserService userService;


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
        final var frontUser = userService.getUserByHash(feedbackInformation.getFeedbackUser());
        if (ObjectUtil.isNotNull(frontUser)) {
            final var frontendUserResponse = new FrontendUserResponse();
            BeanUtils.copyProperties(frontUser, frontendUserResponse);
            feedbackInformationResponse.setFeedbackUser(frontendUserResponse);
        }
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
            final var userHashList = userService.getUserHashListByName(feedbackUser);
            if (CollUtil.isNotEmpty(userHashList)) {
                feedbackInformationLqw.in(FeedbackInformation::getFeedbackUser, userHashList);
            } else {
                return feedbackInformationResponseList;
            }
        }
        feedbackInformationLqw.isNull(FeedbackInformation::getDeleteTime);
        final var feedbackInformationList = list(feedbackInformationLqw);
        if (CollUtil.isNotEmpty(feedbackInformationList)) {
            feedbackInformationList.forEach(feedbackInformation -> {
                final var feedbackInformationResponse = new FeedbackInformationResponse();
                BeanUtils.copyProperties(feedbackInformation, feedbackInformationResponse);
                feedbackInformationResponse.setFeedbackPhotograph(stringToJsonList(feedbackInformation.getFeedbackPhotograph()));
                final var wheelsUser = userService.getUserByHash(feedbackInformation.getFeedbackUser());
                if (ObjectUtil.isNotNull(wheelsUser)) {
                    final var frontendUserResponse = new FrontendUserResponse();
                    BeanUtils.copyProperties(wheelsUser, frontendUserResponse);
                    feedbackInformationResponse.setFeedbackUser(frontendUserResponse);
                }
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
        //TODO:处理人暂时使用ID，后面整合后使用用户hash
        feedbackInformation.setIsHandle(true)
                .setHandleBy(tokenService.getUserId().toString())
                .setHandleTime(LocalDateTime.now())
                .setHandleResult(feedbackInformationRequest.getHandleResult())
                .setHandleRemark(feedbackInformationRequest.getHandleRemark())
                .setUpdateBy(tokenService.getUserId().toString())
                .setUpdateTime(LocalDateTime.now());
        return updateById(feedbackInformation);
    }
}




