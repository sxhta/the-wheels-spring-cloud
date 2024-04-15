package com.sxhta.cloud.wheels.backend.controller.routeconfig;

import com.sxhta.cloud.common.web.controller.BaseController;
import com.sxhta.cloud.common.web.controller.ICommonController;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.web.bind.annotation.*;
import jakarta.inject.Inject;
import io.swagger.v3.oas.annotations.tags.Tag;
import com.sxhta.cloud.common.web.domain.CommonResponse;
import com.sxhta.cloud.common.web.page.TableDataInfo;

import java.io.Serializable;
import java.io.Serial;
import com.sxhta.cloud.common.web.page.PageRequest;
import com.sxhta.cloud.wheels.backend.request.RouteRequest;
import com.sxhta.cloud.wheels.backend.response.RouteResponse;
import com.sxhta.cloud.wheels.backend.request.RouteSearchRequest;
import com.sxhta.cloud.wheels.backend.service.routeconfig.RouteService;

import org.springframework.web.bind.annotation.RestController;

/**
 * 行程配置表 前端控制器
 */
@Tag(name = "行程配置表", description = "行程配置表"+"控制器")
@RestController
@RequestMapping("/route")
public class RouteController extends BaseController implements ICommonController<RouteSearchRequest,RouteRequest,RouteResponse> ,Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Inject
    private RouteService routeService;

    @Override
    @PostMapping("/create")
    @Operation(summary = "新增")
    public CommonResponse<Boolean> create(@RequestBody RouteRequest request) {
        return CommonResponse.result(routeService.create(request));
    }

    @Override
    @DeleteMapping("/delete")
    @Operation(summary = "删除")
    public CommonResponse<Boolean> deleteByHash(@RequestParam(value = "hash") String hash) {
        return CommonResponse.result(routeService.deleteByHash(hash));
    }

    @PutMapping("/update")
    @Operation(summary = "修改")
    public CommonResponse<Boolean> updateEntity(@RequestBody RouteRequest routeRequest) {
        return CommonResponse.result(routeService.updateEntity(routeRequest));
    }

    @Override
    @GetMapping("/info")
    @Operation(summary = "详情")
    public CommonResponse<RouteResponse> getInfoByHash(@RequestParam("hash") String hash) {
        return CommonResponse.success(routeService.getInfoByHash(hash));
    }

    @Override
    @GetMapping("/list")
    @Operation(summary = "列表")
    public TableDataInfo<RouteResponse> getAdminList(@ModelAttribute("RouteSearchRequest") RouteSearchRequest request,PageRequest pageRequest) {
        startPage(pageRequest);
        return CommonResponse.list(routeService.getAdminList(request));
    }

    @Override
    @DeleteMapping("/soft")
    @Operation(summary = "软删除")
    public CommonResponse<Boolean> softDeleteByHash(String hash) {
        return null;
    }
}
