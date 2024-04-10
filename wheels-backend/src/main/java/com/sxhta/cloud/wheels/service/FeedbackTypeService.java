package com.sxhta.cloud.wheels.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.sxhta.cloud.common.service.ICommonService;
import com.sxhta.cloud.wheels.entity.feedback.FeedbackType;
import com.sxhta.cloud.wheels.request.feedback.FeedbackTypeRequest;
import com.sxhta.cloud.wheels.request.feedback.FeedbackTypeSearchRequest;
import com.sxhta.cloud.wheels.response.feedback.FeedbackTypeResponse;

public interface FeedbackTypeService extends ICommonService<FeedbackTypeSearchRequest, FeedbackTypeRequest, FeedbackTypeResponse>, IService<FeedbackType> {

}
