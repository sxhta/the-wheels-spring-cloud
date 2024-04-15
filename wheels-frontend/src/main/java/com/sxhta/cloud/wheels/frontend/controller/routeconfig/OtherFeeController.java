package com.sxhta.cloud.wheels.frontend.controller.routeconfig;

import com.sxhta.cloud.common.web.controller.BaseController;
import com.sxhta.cloud.wheels.frontend.service.routeconfig.OtherFeeService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.inject.Inject;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.Serial;
import java.io.Serializable;

/**
 * 其他费用配置表 前端控制器
 */
@Tag(name = "其他费用配置表", description = "其他费用配置表"+"控制器")
@RestController
@RequestMapping("/other/fee")
public class OtherFeeController extends BaseController implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Inject
    private OtherFeeService otherFeeService;

//    @GetMapping("/list")
//    @Operation(summary = "列表")
//    public TableDataInfo<OtherFeeResponse> getAdminList(OtherFeeSearchRequest request,PageRequest pageRequest) {
//        startPage(pageRequest);
//        return CommonResponse.list(otherFeeService.getAdminList(request));
//    }
}
