package com.sxhta.cloud.wheels.controller.feedback;

import cn.hutool.core.util.StrUtil;
import com.sxhta.cloud.common.exception.ServiceException;
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
import org.springframework.web.bind.annotation.*;

import java.io.Serializable;
import java.util.List;

@RestController
@RequestMapping("/feedback/type")
@Tag(name = "反馈类型", description = "反馈类型管理器")
public class FeedbackTypeController extends BaseController implements ICommonController<FeedbackTypeSearchRequest, FeedbackTypeRequest, FeedbackTypeResponse>, Serializable {

    @Inject
    private FeedbackTypeService feedbackTypeService;


    @Override
    @GetMapping("/list")
    public TableDataInfo<FeedbackTypeResponse> getAdminList(@ModelAttribute("FeedbackTypeSearchRequest") FeedbackTypeSearchRequest request, PageRequest pageRequest) {
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
    @PostMapping("/save")
    public CommonResponse<Boolean> create(@RequestBody FeedbackTypeRequest feedbackTypeRequest) {
        return CommonResponse.result(feedbackTypeService.create(feedbackTypeRequest));
    }

    @Override
    @DeleteMapping("/delete")
    public CommonResponse<Boolean> softDeleteByHash(@RequestParam String hash) {
        if (StrUtil.isBlank(hash)) {
            throw new ServiceException("该反馈类型异常，请联系管理员");
        }
        return CommonResponse.result(feedbackTypeService.softDeleteByHash(hash));
    }

    @PutMapping("/update")
    public CommonResponse<Boolean> updateEntityByHash(@RequestBody FeedbackTypeRequest feedbackTypeRequest) {
        return CommonResponse.result(feedbackTypeService.updateEntity(feedbackTypeRequest));
    }

    @Override
    public CommonResponse<Boolean> deleteByHash(String hash) {
        return null;
    }

    @Override
    public CommonResponse<Boolean> updateCategory(FeedbackTypeRequest feedbackTypeRequest) {
        return null;
    }


    //TODO:修改状态
    @PutMapping("/status")
    public CommonResponse<Boolean> updateStatus(String hash) {
        return CommonResponse.result(feedbackTypeService.updateStatus(hash));
    }


    @GetMapping("/all")
    public CommonResponse<List<FeedbackTypeResponse>> getAll() {
        return CommonResponse.success(feedbackTypeService.getAll());
    }

}
