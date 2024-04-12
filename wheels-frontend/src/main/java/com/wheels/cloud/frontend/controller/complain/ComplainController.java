package com.wheels.cloud.frontend.controller.complain;

import com.sxhta.cloud.common.web.domain.CommonResponse;
import com.wheels.cloud.frontend.request.complain.ComplainInformationRequest;
import com.wheels.cloud.frontend.service.complain.ComplainInformationService;
import jakarta.inject.Inject;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/complain")
public class ComplainController {
    @Inject
    private ComplainInformationService complainInformationService;


    //新增投诉信息
    @PostMapping("/save")
    public CommonResponse<Boolean> userComplainCreate(@RequestBody ComplainInformationRequest request) {
        return CommonResponse.result(complainInformationService.userComplainCreate(request));
    }

    //用户的投诉列表
    @GetMapping("/user/list")
    public void userComplainList() {
        complainInformationService.userComplainList();
    }

    //司机的投诉
    @GetMapping("/driver/list")
    public void driverComplainList() {
        complainInformationService.driverComplainList();
    }


}
