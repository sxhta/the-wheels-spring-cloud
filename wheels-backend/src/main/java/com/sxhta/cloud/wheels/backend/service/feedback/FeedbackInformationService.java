package com.sxhta.cloud.wheels.backend.service.feedback;

import com.baomidou.mybatisplus.extension.service.IService;
import com.sxhta.cloud.common.service.ICommonService;
import com.sxhta.cloud.wheels.backend.entity.feedback.FeedbackInformation;
import com.sxhta.cloud.wheels.backend.request.feedback.FeedbackInformationRequest;
import com.sxhta.cloud.wheels.backend.request.feedback.FeedbackInformationSearchRequest;
import com.sxhta.cloud.wheels.backend.response.feedback.FeedbackInformationResponse;

public interface FeedbackInformationService extends ICommonService<FeedbackInformationSearchRequest, FeedbackInformationRequest, FeedbackInformationResponse>, IService<FeedbackInformation> {

    Boolean handleFeedbackInformation(FeedbackInformationRequest feedbackInformationRequest);
}
