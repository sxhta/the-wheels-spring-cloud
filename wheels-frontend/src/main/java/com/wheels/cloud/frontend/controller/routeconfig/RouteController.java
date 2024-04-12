package com.wheels.cloud.frontend.controller.routeconfig;

import com.sxhta.cloud.common.web.controller.BaseController;
import com.sxhta.cloud.common.web.domain.CommonResponse;
import com.sxhta.cloud.common.web.page.PageRequest;
import com.sxhta.cloud.common.web.page.TableDataInfo;
import com.wheels.cloud.frontend.request.routeconfig.RouteSearchRequest;
import com.wheels.cloud.frontend.response.routeconfig.RouteResponse;
import com.wheels.cloud.frontend.service.routeconfig.RouteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.inject.Inject;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.Serial;
import java.io.Serializable;

/**
 * 行程配置表 前端控制器
 */
@Tag(name = "行程配置表", description = "行程配置表" + "控制器")
@RestController
@RequestMapping("/front/route")
public class RouteController extends BaseController implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Inject
    private RouteService routeService;


    @Operation(summary = "出发地列表")
    @GetMapping("/departure/list")
    public TableDataInfo<RouteResponse> getDepartureList(@RequestParam(value = "destinationHash",defaultValue = "") String destinationHash, PageRequest pageRequest) {
        startPage(pageRequest);
        return CommonResponse.list(routeService.getDepartureList(destinationHash));
    }

    @Operation(summary = "目的地列表")
    @GetMapping("/destination/list")
    public TableDataInfo<RouteResponse> getDestinationList(@RequestParam(value = "departureHash",defaultValue = "") String departureHash, PageRequest pageRequest) {
        startPage(pageRequest);
        return CommonResponse.list(routeService.getDestinationList(departureHash));
    }

//    @GetMapping("/list")
//    @Operation(summary = "列表")
//    public TableDataInfo<RouteResponse> getAdminList(RouteSearchRequest request, PageRequest pageRequest) {
//        startPage(pageRequest);
//        return CommonResponse.list(routeService.getAdminList(request));
//    }
}
