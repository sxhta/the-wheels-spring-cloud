package com.wheels.cloud.order.controller;

import com.sxhta.cloud.common.web.controller.BaseController;
import com.sxhta.cloud.common.web.controller.ICommonController;
import com.sxhta.cloud.common.web.domain.CommonResponse;
import com.sxhta.cloud.common.web.page.PageRequest;
import com.sxhta.cloud.common.web.page.TableDataInfo;
import com.sxhta.cloud.wheels.remote.response.OrderResponse;
import com.wheels.cloud.order.request.OrderRequest;
import com.wheels.cloud.order.request.OrderSearchRequest;
import com.wheels.cloud.order.service.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.inject.Inject;
import org.springframework.web.bind.annotation.*;

import java.io.Serial;
import java.io.Serializable;

/**
 * 订单管理 前端控制器
 */
@Tag(name = "订单管理", description = "订单管理"+"控制器")
@RestController
@RequestMapping("/order")
public class OrderController extends BaseController implements ICommonController<OrderSearchRequest,OrderRequest, OrderResponse> ,Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Inject
    private OrderService orderService;

    @Override
    @PostMapping("/create")
    @Operation(summary = "新增")
    public CommonResponse<Boolean> create(@RequestBody OrderRequest request) {
        return CommonResponse.result(orderService.create(request));
    }

    @Override
    @DeleteMapping("/delete")
    @Operation(summary = "删除")
    public CommonResponse<Boolean> deleteByHash(@RequestParam(value = "hash") String hash) {
        return CommonResponse.result(orderService.deleteByHash(hash));
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
    public CommonResponse<Boolean> updateCategory(@RequestBody OrderRequest request) {
        return CommonResponse.result(orderService.updateEntity(request));
    }

    @Override
    @PutMapping("/list")
    @Operation(summary = "修改")
    public TableDataInfo<OrderResponse> getAdminList(OrderSearchRequest request, PageRequest pageRequest) {
        return null;
    }

    @Override
    @GetMapping("/info")
    @Operation(summary = "详情")
    public CommonResponse<OrderResponse> getInfoByHash(@RequestParam("hash") String hash) {
        return CommonResponse.success(orderService.getInfoByHash(hash));
    }

    @GetMapping("/front/list")
    @Operation(summary = "客户端列表")
    public TableDataInfo<OrderResponse> getAdminList(@RequestParam(value = "type",defaultValue = "") Integer type,//1已完成，2已取消
                                                     PageRequest pageRequest) {
        startPage(pageRequest);
        return CommonResponse.list(orderService.getFrontList(type));
    }
}
