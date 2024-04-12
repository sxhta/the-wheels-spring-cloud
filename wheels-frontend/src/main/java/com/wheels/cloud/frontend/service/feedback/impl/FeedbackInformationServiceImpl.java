package com.wheels.cloud.frontend.service.feedback.impl;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sxhta.cloud.security.service.TokenService;
import com.sxhta.cloud.wheels.remote.domain.feedback.FeedbackInformation;
import com.sxhta.cloud.wheels.remote.domain.user.WheelsFrontUser;
import com.sxhta.cloud.wheels.remote.request.feedback.FeedbackInformationRequest;
import com.sxhta.cloud.wheels.remote.response.feedback.FeedbackInformationResponse;
import com.sxhta.cloud.wheels.remote.vo.FrontUserCacheVo;
import com.wheels.cloud.frontend.mapper.feedback.FeedbackInformationMapper;
import com.wheels.cloud.frontend.service.feedback.FeedbackInformationService;
import jakarta.inject.Inject;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import static com.sxhta.cloud.common.utils.CharacterConvert.listToJsonString;
import static com.sxhta.cloud.common.utils.CharacterConvert.stringToJsonList;

@Service
public class FeedbackInformationServiceImpl extends ServiceImpl<FeedbackInformationMapper, FeedbackInformation> implements FeedbackInformationService, Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Inject
    private TokenService<FrontUserCacheVo, WheelsFrontUser> tokenService;

    @Override
    public Boolean userFeedbackCreate(FeedbackInformationRequest request) {
        final var feedbackInformation = new FeedbackInformation();
        BeanUtils.copyProperties(request, feedbackInformation);
        feedbackInformation.setFeedbackPhotograph(listToJsonString(request.getFeedbackPhotograph()))
                .setCreateBy(tokenService.getUsername());
        return save(feedbackInformation);
    }


    @Override
    public List<FeedbackInformationResponse> getMyFeedbackList() {
        final var feedbackInformationResponseList = new ArrayList<FeedbackInformationResponse>();
        final var feedbackInformationLqw = new LambdaQueryWrapper<FeedbackInformation>();
        feedbackInformationLqw.eq(FeedbackInformation::getCreateBy, tokenService.getUsername())
                .isNull(FeedbackInformation::getDeleteTime);
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


}
