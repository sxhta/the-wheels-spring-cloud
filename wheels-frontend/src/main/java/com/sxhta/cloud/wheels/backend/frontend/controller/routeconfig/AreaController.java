package com.sxhta.cloud.wheels.frontend.controller.routeconfig;

import com.sxhta.cloud.common.web.controller.BaseController;
import com.sxhta.cloud.wheels.frontend.service.routeconfig.AreaService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.inject.Inject;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.Serial;
import java.io.Serializable;

/**
 * 地区配置表 前端控制器
 */
@Tag(name = "地区配置表", description = "地区配置表" + "控制器")
@RestController
@RequestMapping("/area")
public class AreaController extends BaseController implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Inject
    private AreaService areaService;


//    @GetMapping("/list")
//    @Operation(summary = "列表")
//    public TableDataInfo<AreaResponse> getAdminList(AreaSearchRequest request,PageRequest pageRequest) {
//        startPage(pageRequest);
//        return CommonResponse.list(areaService.getAdminList(request));
//    }
}
