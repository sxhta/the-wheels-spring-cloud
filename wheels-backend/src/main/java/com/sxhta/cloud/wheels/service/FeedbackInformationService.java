package com.sxhta.cloud.wheels.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.sxhta.cloud.common.service.ICommonService;
import com.sxhta.cloud.wheels.entity.feedback.FeedbackInformation;
import com.sxhta.cloud.wheels.request.feedback.FeedbackInformationRequest;
import com.sxhta.cloud.wheels.request.feedback.FeedbackInformationSearchRequest;
import com.sxhta.cloud.wheels.response.feedback.FeedbackInformationResponse;

public interface FeedbackInformationService extends ICommonService<FeedbackInformationSearchRequest, FeedbackInformationRequest, FeedbackInformationResponse>, IService<FeedbackInformation> {

    Boolean handleFeedbackInformation(FeedbackInformationRequest feedbackInformationRequest);
}
