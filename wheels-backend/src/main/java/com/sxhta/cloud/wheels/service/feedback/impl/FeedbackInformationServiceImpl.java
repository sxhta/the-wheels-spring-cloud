package com.sxhta.cloud.wheels.service.feedback.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
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
import com.sxhta.cloud.wheels.service.feedback.FeedbackInformationService;
import jakarta.inject.Inject;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class FeedbackInformationServiceImpl extends ServiceImpl<FeedbackInformationMapper, FeedbackInformation> implements FeedbackInformationService {

    @Inject
    private TokenService<SystemUserCacheVo, SysUser> tokenService;


    @Override
    public Boolean create(FeedbackInformationRequest feedbackInformationRequest) {
        final var feedbackInformation = new FeedbackInformation();
        BeanUtils.copyProperties(feedbackInformationRequest, feedbackInformation);
        feedbackInformation.setCreateBy(tokenService.getUsername());
        //TODO:反馈截图数组转字符
        return save(feedbackInformation);
    }

    @Override
    public FeedbackInformationResponse getInfoByHash(String hash) {
        final var feedbackInformation = getEntity(hash);
        final var feedbackInformationResponse = new FeedbackInformationResponse();
        BeanUtils.copyProperties(feedbackInformation, feedbackInformationResponse);
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
        final var feedbackInformationResponseList = new ArrayList<FeedbackInformationResponse>();
        final var feedbackInformationLqw = new LambdaQueryWrapper<FeedbackInformation>();
        //TODO:反馈信息模糊查询参数
        final var feedbackInformationList = list(feedbackInformationLqw);
        if (CollUtil.isNotEmpty(feedbackInformationList)) {
            feedbackInformationList.forEach(feedbackInformation -> {
                final var feedbackInformationResponse = new FeedbackInformationResponse();
                BeanUtils.copyProperties(feedbackInformation, feedbackInformationResponse);
                //TODO:反馈图片字符转数组，时间格式
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
        feedbackInformation.setHandleBy("处理人")
                .setHandleTime(LocalDateTime.now())
                .setHandleRemark("处理结果");
        return updateById(feedbackInformation);
    }
}




