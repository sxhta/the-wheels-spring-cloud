package com.sxhta.cloud.wheels.frontend.controller.complain;

import com.sxhta.cloud.common.web.domain.CommonResponse;
import com.sxhta.cloud.common.web.page.PageRequest;
import com.sxhta.cloud.common.web.page.TableDataInfo;
import com.sxhta.cloud.wheels.frontend.request.complain.ComplainInformationRequest;
import com.sxhta.cloud.wheels.frontend.response.complain.ComplainInformationResponse;
import com.sxhta.cloud.wheels.frontend.response.complain.ComplainTypeResponse;
import com.sxhta.cloud.wheels.frontend.service.complain.ComplainInformationService;
import jakarta.inject.Inject;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.github.pagehelper.page.PageMethod.startPage;

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
    public TableDataInfo<ComplainInformationResponse> userComplainList(PageRequest pageRequest) {
        startPage(pageRequest);
        return CommonResponse.list(complainInformationService.userComplainList());
    }

    @GetMapping("/info")
    public CommonResponse<ComplainInformationResponse> getComplainInfo(@RequestParam String hash) {
        return CommonResponse.success("查询成功", complainInformationService.getComplainInfo(hash));
    }

    @GetMapping("/type/list")
    public CommonResponse<List<ComplainTypeResponse>> getComplainTypeList() {
        return CommonResponse.success("查询成功", complainInformationService.getComplainTypeList());
    }


    //司机的投诉
    @GetMapping("/driver/list")
    public void driverComplainList() {
        complainInformationService.driverComplainList();
    }


}
