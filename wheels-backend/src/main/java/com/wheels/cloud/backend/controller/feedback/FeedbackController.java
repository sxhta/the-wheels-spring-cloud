package com.wheels.cloud.backend.controller.feedback;

import com.sxhta.cloud.common.web.domain.AjaxResult;
import com.wheels.cloud.backend.dto.FeedbackDto;
import com.wheels.cloud.backend.dto.FeedbackTypeDto;
import com.wheels.cloud.backend.service.IFeedbackInformationService;
import com.wheels.cloud.backend.service.IFeedbackTypeService;
import jakarta.inject.Inject;
import org.springframework.web.bind.annotation.*;

import static com.sxhta.cloud.common.web.domain.AjaxResult.error;
import static com.sxhta.cloud.common.web.domain.AjaxResult.success;

@RestController
@RequestMapping("/feedback")
public class FeedbackController {
    @Inject
    private IFeedbackInformationService feedbackInformationService;
    @Inject
    private IFeedbackTypeService feedbackTypeService;


    /**
     * 反馈信息新增
     *
     * @param feedbackDto 反馈信息web数据
     * @return true新增成功 false新增失败
     */
    @PostMapping("/saveFeedback")
    public AjaxResult saveFeedback(@RequestBody FeedbackDto feedbackDto) {
        //TODO:反馈信息web数据，判断必填项数据是否填写，其他数据是否填写正确
        if (feedbackInformationService.saveFeedback(feedbackDto)) {
            return success("新增成功");
        }
        return error("新增失败");
    }

    /**
     * 反馈信息删除
     *
     * @param feedbackCode 反馈编号
     * @return true删除成功 false删除失败
     */

    @DeleteMapping("/deleteFeedback")
    public AjaxResult deleteFeedback(@RequestParam String feedbackCode) {
        //TODO:判断articleCode是否为null
        if (feedbackInformationService.deleteFeedback(feedbackCode)) {
            return success("删除成功");
        }
        return error("删除失败");
    }

    /**
     * 反馈信息修改
     *
     * @param feedbackDto 反馈信息web数据
     * @return true修改成功 false修改失败
     */
    @PutMapping("/updateFeedback")
    public AjaxResult updateFeedback(@RequestBody FeedbackDto feedbackDto) {
        //TODO:反馈信息web数据，判断必填项数据是否填写，其他数据是否填写正确
        if (feedbackInformationService.updateFeedback(feedbackDto)) {
            return success("修改成功");
        }
        return error("修改失败");
    }

    //反馈信息列表
    @GetMapping("/feedbackList")
    public AjaxResult selectFeedbackAll() {
        return success("查询成功", feedbackInformationService.selectFeedbackAll());
    }

    //反馈信息详情
    @GetMapping("/feedbackInfo")
    public AjaxResult selectFeedbackInfo() {
        return success("查询成功", feedbackInformationService.selectFeedbackInfo());
    }

    /**
     * 反馈信息类型新增
     *
     * @param feedbackTypeDto 反馈信息类型web数据
     * @return true新增成功 false新增失败
     */
    @PostMapping("/saveType")
    public AjaxResult saveFeedbackType(@RequestBody FeedbackTypeDto feedbackTypeDto) {
        //TODO:反馈信息类型web数据，判断必填项数据是否填写，其他数据是否填写正确
        if (feedbackTypeService.saveFeedbackType(feedbackTypeDto)) {
            return success("新增成功");
        }
        return error("新增失败");
    }

    /**
     * 反馈信息类型删除
     *
     * @param feedbackTypeCode 反馈信息类型编号
     * @return true删除成功 false删除失败
     */
    @DeleteMapping("/deleteType")
    public AjaxResult deleteFeedbackType(@RequestParam String feedbackTypeCode) {
        //TODO:判断articleCode是否为null
        if (feedbackTypeService.deleteFeedbackType(feedbackTypeCode)) {
            return success("删除成功");
        }
        return error("删除失败");
    }

    /**
     * 反馈信息类型修改
     *
     * @param feedbackTypeDto 反馈信息类型web数据
     * @return true修改成功 false修改失败
     */
    @PutMapping("/updateType")
    public AjaxResult updateFeedbackType(@RequestBody FeedbackTypeDto feedbackTypeDto) {
        //TODO:反馈信息类型web数据，判断必填项数据是否填写，其他数据是否填写正确
        if (feedbackTypeService.updateFeedbackType(feedbackTypeDto)) {
            return success("修改成功");
        }
        return error("修改失败");
    }

    //反馈信息类型修改列表
    @GetMapping("/typeList")
    public AjaxResult selectFeedbackTypeAll() {
        return success("查询成功", feedbackTypeService.selectFeedbackTypeAll());
    }

    //反馈信息类型详情
    @GetMapping("/typeInfo")
    public AjaxResult selectFeedbackTypeInfo() {
        return success("查询成功", feedbackTypeService.selectFeedbackTypeInfo());
    }


}
