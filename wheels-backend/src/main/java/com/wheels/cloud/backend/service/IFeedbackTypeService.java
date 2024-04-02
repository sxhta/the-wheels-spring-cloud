package com.wheels.cloud.backend.service;

import com.wheels.cloud.backend.response.FeedbackTypeDto;

public interface IFeedbackTypeService {
    Boolean saveFeedbackType(FeedbackTypeDto feedbackTypeDto);

    Boolean deleteFeedbackType(String feedbackTypeCode);

    Boolean updateFeedbackType(FeedbackTypeDto feedbackTypeDto);

    Object selectFeedbackTypeAll();

    Object selectFeedbackTypeInfo();
}
