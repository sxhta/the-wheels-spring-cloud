package com.sxhta.cloud.wheels.backend.controller.feedback;

import com.sxhta.cloud.common.web.controller.BaseController;
import com.sxhta.cloud.common.web.controller.ICommonController;
import com.sxhta.cloud.common.web.domain.CommonResponse;
import com.sxhta.cloud.common.web.page.PageRequest;
import com.sxhta.cloud.common.web.page.TableDataInfo;
import com.sxhta.cloud.wheels.backend.request.feedback.FeedbackInformationRequest;
import com.sxhta.cloud.wheels.backend.request.feedback.FeedbackInformationSearchRequest;
import com.sxhta.cloud.wheels.backend.response.feedback.FeedbackInformationResponse;
import com.sxhta.cloud.wheels.backend.service.feedback.FeedbackInformationService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.inject.Inject;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.io.Serializable;

@RestController
@RequestMapping("/feedback")
@Tag(name = "反馈信息", description = "反馈信息管理器")
public class FeedbackInformationController extends BaseController implements ICommonController<FeedbackInformationSearchRequest, FeedbackInformationRequest, FeedbackInformationResponse>, Serializable {

    @Inject
    private FeedbackInformationService feedbackInformationService;

    @Override
    @GetMapping("/list")
    public TableDataInfo<FeedbackInformationResponse> getAdminList(FeedbackInformationSearchRequest request, @Validated PageRequest pageRequest) {
        startPage(pageRequest);
        final var list = feedbackInformationService.getAdminList(request);
        return CommonResponse.list(list);
    }

    @Override
    @GetMapping("/info")
    public CommonResponse<FeedbackInformationResponse> getInfoByHash(@RequestParam String hash) {
        return CommonResponse.success("查询成功", feedbackInformationService.getInfoByHash(hash));
    }

    @Override
    @PostMapping("/save")
    public CommonResponse<Boolean> create(@RequestBody FeedbackInformationRequest feedbackInformationRequest) {
        final var result = feedbackInformationService.create(feedbackInformationRequest);
        return CommonResponse.success(result);
    }

    @Override
    @DeleteMapping("/soft")
    public CommonResponse<Boolean> softDeleteByHash(String hash) {

        return null;
    }

    @Override
    @DeleteMapping("/delete")
    public CommonResponse<Boolean> deleteByHash(String hash) {
        return null;
    }

    @Override
    @PutMapping("/update")
    public CommonResponse<Boolean> updateEntity(FeedbackInformationRequest feedbackInformationRequest) {
        feedbackInformationService.updateEntity(feedbackInformationRequest);
        return null;
    }


    //TODO:处理反馈信息
    @PutMapping("/handle")
    public CommonResponse<Boolean> handleFeedbackInformation(@RequestBody FeedbackInformationRequest feedbackInformationRequest) {
        return CommonResponse.result(feedbackInformationService.handleFeedbackInformation(feedbackInformationRequest));
    }

    @GetMapping("/")
    public TableDataInfo<FeedbackInformationResponse> getMyFeedbackList() {
        return null;
    }

}
