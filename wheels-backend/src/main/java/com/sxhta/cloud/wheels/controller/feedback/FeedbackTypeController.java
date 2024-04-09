package com.sxhta.cloud.wheels.controller.feedback;

import com.sxhta.cloud.common.web.controller.BaseController;
import com.sxhta.cloud.common.web.controller.ICommonController;
import com.sxhta.cloud.common.web.domain.CommonResponse;
import com.sxhta.cloud.common.web.page.PageRequest;
import com.sxhta.cloud.common.web.page.TableDataInfo;
import com.sxhta.cloud.wheels.request.feedback.FeedbackTypeRequest;
import com.sxhta.cloud.wheels.request.feedback.FeedbackTypeSearchRequest;
import com.sxhta.cloud.wheels.response.feedback.FeedbackTypeResponse;
import com.sxhta.cloud.wheels.service.feedback.FeedbackTypeService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.inject.Inject;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.Serializable;

@RestController
@RequestMapping("/feedback/type")
@Tag(name = "反馈类型", description = "反馈类型管理器")
public class FeedbackTypeController extends BaseController implements ICommonController<FeedbackTypeSearchRequest, FeedbackTypeRequest, FeedbackTypeResponse>, Serializable {

    @Inject
    private FeedbackTypeService feedbackTypeService;


    @Override
    public TableDataInfo<FeedbackTypeResponse> getAdminList(FeedbackTypeSearchRequest request, PageRequest pageRequest) {
        startPage(pageRequest);
        final var list = feedbackTypeService.getAdminList(request);
        return CommonResponse.list(list);
    }

    @Override
    public CommonResponse<FeedbackTypeResponse> getInfoByHash(String hash) {
        feedbackTypeService.getInfoByHash(hash);
        return null;
    }

    @Override
    public CommonResponse<Boolean> create(FeedbackTypeRequest feedbackTypeRequest) {
        feedbackTypeService.create(feedbackTypeRequest);
        return null;
    }

    @Override
    public CommonResponse<Boolean> softDeleteByHash(String hash) {
        feedbackTypeService.softDeleteByHash(hash);
        return null;
    }

    @Override
    public CommonResponse<Boolean> deleteByHash(String hash) {
        feedbackTypeService.deleteByHash(hash);
        return null;
    }

    @Override
    public CommonResponse<Boolean> updateCategory(FeedbackTypeRequest feedbackTypeRequest) {
        feedbackTypeService.updateEntity(feedbackTypeRequest);
        return null;
    }

    //TODO:修改状态
    @PutMapping("/status")
    public CommonResponse<Boolean> updateStatus(String hash) {
        feedbackTypeService.updateStatus(hash);
        return null;
    }


}
