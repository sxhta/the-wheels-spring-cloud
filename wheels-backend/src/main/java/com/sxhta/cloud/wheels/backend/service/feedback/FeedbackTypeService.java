package com.sxhta.cloud.wheels.backend.service.feedback;

import com.baomidou.mybatisplus.extension.service.IService;
import com.sxhta.cloud.common.service.ICommonService;
import com.sxhta.cloud.wheels.backend.entity.feedback.FeedbackType;
import com.sxhta.cloud.wheels.backend.request.feedback.FeedbackTypeRequest;
import com.sxhta.cloud.wheels.backend.request.feedback.FeedbackTypeSearchRequest;
import com.sxhta.cloud.wheels.backend.response.feedback.FeedbackTypeResponse;

import java.util.List;

public interface FeedbackTypeService extends ICommonService<FeedbackTypeSearchRequest, FeedbackTypeRequest, FeedbackTypeResponse>, IService<FeedbackType> {

    Boolean updateStatus(String hash);

    List<FeedbackTypeResponse> getAll();
}
