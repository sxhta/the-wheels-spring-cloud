package com.wheels.cloud.backend.service;

import com.wheels.cloud.backend.dto.FeedbackDto;

public interface IFeedbackInformationService {
    Boolean saveFeedback(FeedbackDto feedbackDto);

    Boolean deleteFeedback(String feedbackCode);

    Boolean updateFeedback(FeedbackDto feedbackDto);

    Object selectFeedbackAll();

    Object selectFeedbackInfo();
}
