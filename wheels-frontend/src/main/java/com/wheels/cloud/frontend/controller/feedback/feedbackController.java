package com.wheels.cloud.frontend.controller.feedback;

import com.sxhta.cloud.common.web.domain.CommonResponse;
import com.sxhta.cloud.wheels.remote.request.feedback.FeedbackInformationRequest;
import com.wheels.cloud.frontend.service.feedback.FeedbackInformationService;
import jakarta.inject.Inject;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/feedback/user")
public class feedbackController {

    @Inject
    private FeedbackInformationService feedbackInformationService;


    //用户反馈
    @PostMapping("/create")
    public CommonResponse<Boolean> userFeedbackCreate(@RequestBody FeedbackInformationRequest request) {
        return CommonResponse.result(feedbackInformationService.userFeedbackCreate(request));
    }


    //我的反馈列表
    @GetMapping("/my")
    public void getMyFeedbackList() {
        feedbackInformationService.getMyFeedbackList();
    }


}
