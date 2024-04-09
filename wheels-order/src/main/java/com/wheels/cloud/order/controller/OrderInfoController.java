package com.wheels.cloud.order.controller;

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
import com.wheels.cloud.order.request.OrderInfoRequest;
import com.wheels.cloud.order.response.OrderInfoResponse;
import com.wheels.cloud.order.request.OrderInfoSearchRequest;
import com.wheels.cloud.order.service.OrderInfoService;

import org.springframework.web.bind.annotation.RestController;

/**
 * 订单详情 前端控制器
 */
@Tag(name = "订单详情", description = "订单详情"+"控制器")
@RestController
@RequestMapping("/order-info")
public class OrderInfoController extends BaseController implements ICommonController<OrderInfoSearchRequest,OrderInfoRequest,OrderInfoResponse> ,Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Inject
    private OrderInfoService orderInfoService;

    @Override
    @PostMapping("/create")
    @Operation(summary = "新增")
    public CommonResponse<Boolean> create(@RequestBody OrderInfoRequest request) {
        return CommonResponse.result(orderInfoService.create(request));
    }

    @Override
    @DeleteMapping("/delete")
    @Operation(summary = "删除")
    public CommonResponse<Boolean> deleteByHash(@RequestParam(value = "hash") String hash) {
        return CommonResponse.result(orderInfoService.deleteByHash(hash));
    }

    @Override
    @DeleteMapping("/soft")
    @Operation(summary = "软删除")
    public CommonResponse<Boolean> softDeleteByHash(String hash) {
        return null;
    }


    @Override
    @PutMapping("/update")
    @Operation(summary = "修改")
    public CommonResponse<Boolean> updateCategory(@RequestBody OrderInfoRequest request) {
        return CommonResponse.result(orderInfoService.updateEntity(request));
    }

    @Override
    @GetMapping("/info")
    @Operation(summary = "详情")
    public CommonResponse<OrderInfoResponse> getInfoByHash(@RequestParam("hash") String hash) {
        return CommonResponse.success(orderInfoService.getInfoByHash(hash));
    }

    @Override
    @GetMapping("/list")
    @Operation(summary = "列表")
    public TableDataInfo<OrderInfoResponse> getAdminList(OrderInfoSearchRequest request,PageRequest pageRequest) {
        startPage(pageRequest);
        return CommonResponse.list(orderInfoService.getAdminList(request));
    }
}
