package com.wheels.cloud.frontend.service.feedback;

import com.baomidou.mybatisplus.extension.service.IService;
import com.sxhta.cloud.wheels.remote.domain.feedback.FeedbackInformation;
import com.sxhta.cloud.wheels.remote.request.feedback.FeedbackInformationRequest;
import com.sxhta.cloud.wheels.remote.response.feedback.FeedbackInformationResponse;

import java.util.List;

public interface FeedbackInformationService extends IService<FeedbackInformation> {

    Boolean userFeedbackCreate(FeedbackInformationRequest request);

    List<FeedbackInformationResponse> getMyFeedbackList();
}
