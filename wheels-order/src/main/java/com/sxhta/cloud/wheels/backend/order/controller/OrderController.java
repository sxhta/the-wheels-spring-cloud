package com.sxhta.cloud.wheels.order.controller;

import com.sxhta.cloud.common.web.controller.BaseController;
import com.sxhta.cloud.common.web.controller.ICommonController;
import com.sxhta.cloud.common.web.domain.CommonResponse;
import com.sxhta.cloud.common.web.page.PageRequest;
import com.sxhta.cloud.common.web.page.TableDataInfo;
import com.sxhta.cloud.remote.domain.SysFile;
import com.sxhta.cloud.security.annotation.InnerAuth;
import com.sxhta.cloud.wheels.order.request.OrderRequest;
import com.sxhta.cloud.wheels.order.service.OrderService;
import com.sxhta.cloud.wheels.remote.request.order.OrderSearchRequest;
import com.sxhta.cloud.wheels.remote.response.order.admin.OrderAdminInfoResponse;
import com.sxhta.cloud.wheels.remote.response.order.admin.OrderAdminResponse;
import com.sxhta.cloud.wheels.remote.response.order.admin.OrderExpectationResponse;
import com.sxhta.cloud.wheels.remote.response.order.front.OrderInfoResponse;
import com.sxhta.cloud.wheels.remote.response.order.front.OrderResponse;
import com.sxhta.cloud.wheels.remote.response.order.owner.OrderOwnerResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.inject.Inject;
import org.springframework.cloud.openfeign.SpringQueryMap;
import org.springframework.web.bind.annotation.*;

import java.io.Serial;
import java.io.Serializable;
import java.text.ParseException;

/**
 * 订单管理 前端控制器
 */
@Tag(name = "订单管理", description = "订单管理" + "控制器")
@RestController
@RequestMapping("/orders")
public class OrderController extends BaseController implements ICommonController<OrderSearchRequest, OrderRequest, OrderResponse>, Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Inject
    private OrderService orderService;

    @Override
    @PostMapping("/create")
    @Operation(summary = "新增")
    public CommonResponse<Boolean> create(@RequestBody OrderRequest request) {
        return CommonResponse.result(null);
    }

    @Override
    @DeleteMapping("/delete")
    @Operation(summary = "删除")
    public CommonResponse<Boolean> deleteByHash(@RequestParam(value = "hash") String hash) {
        return CommonResponse.result(null);
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
    public CommonResponse<Boolean> updateEntity(@RequestBody OrderRequest request) {
        return CommonResponse.result(null);
    }

    @Override
    public TableDataInfo<OrderResponse> getAdminList(OrderSearchRequest request, PageRequest pageRequest) {
        return null;
    }

    @Operation(summary = "详情")
    @Override
    @GetMapping("/info")
    public CommonResponse<OrderResponse> getInfoByHash(@RequestParam("hash") String hash) {
        return CommonResponse.success(null);
    }

    @Operation(summary = "客户端订单列表")
    @InnerAuth
    @GetMapping("/user/front/list")
    public CommonResponse<TableDataInfo<OrderResponse>> getFrontList(@RequestParam(value = "userHash") String userHash,
                                                                     @RequestParam(value = "type", defaultValue = "") Integer type,//1已完成，2已取消
                                                                     PageRequest pageRequest) {
        startPage(pageRequest);
        return CommonResponse.success(CommonResponse.list(orderService.getFrontList(userHash, type)));
    }

    @Operation(summary = "客户端订单详情")
    @InnerAuth
    @GetMapping("/user/front/info/{orderHash}")
    public CommonResponse<OrderInfoResponse> getFrontInfo(@PathVariable(value = "orderHash") String orderHash) {
        final var frontInfo = orderService.getFrontInfo(orderHash);
        return CommonResponse.success(frontInfo);
    }

    @Operation(summary = "客户端待出行列表")
    @InnerAuth
    @GetMapping("/user/front/expectation/list")
    public CommonResponse<TableDataInfo<OrderExpectationResponse>> getFrontExpectationList(@RequestParam(value = "userHash") String userHash,
                                                                                           PageRequest pageRequest) {
        startPage(pageRequest);
        return CommonResponse.success(CommonResponse.list(orderService.getFrontExpectationList(userHash)));
    }

    @Operation(summary = "客户端总里程")

    @GetMapping("/user/front/total/mileage")
    public CommonResponse<Double> getFrontTotalMileage(@RequestParam(value = "userHash") String userHash) {
        return CommonResponse.success(orderService.getFrontTotalMileage(userHash));
    }

    @Operation(summary = "后管订单列表")
    @InnerAuth
    @GetMapping("/admin/list")
    public CommonResponse<TableDataInfo<OrderAdminResponse>> getBackstageList(@SpringQueryMap OrderSearchRequest request, PageRequest pageRequest) throws ParseException {
        startPage(pageRequest);
        return CommonResponse.success(CommonResponse.list(orderService.getBackstageList(request)));
    }

    @Operation(summary = "后管订单详情")
    @InnerAuth
    @GetMapping("/admin/info/{orderHash}")
    public CommonResponse<OrderAdminInfoResponse> getBackstageInfo(@PathVariable(value = "orderHash") String orderHash) {
        return CommonResponse.success(orderService.getBackstageInfo(orderHash));
    }

    @Operation(summary = "后管订单导出")
    @InnerAuth
    @GetMapping("/admin/export")
    public CommonResponse<SysFile> getBackstageExport(@SpringQueryMap OrderSearchRequest request) throws ParseException {
        return CommonResponse.success(orderService.getBackstageExport(request));
    }


    @Operation(summary = "司机端列表")
    @InnerAuth
    @GetMapping("/owner/list")
    public CommonResponse<TableDataInfo<OrderOwnerResponse>> getOwnerList(@RequestParam(value = "ownerHash") String ownerHash,
                                                                          @RequestParam(value = "location") Integer location,//1首页，2全部
                                                                          @RequestParam(value = "orderType") Integer orderType,//1即时订单，2预约订单
                                                                          @RequestParam(value = "ownerAcceptStatus") Integer ownerAcceptStatus,//1即时订单，2预约订单
                                                                          PageRequest pageRequest) {
        startPage(pageRequest);
        return CommonResponse.success(CommonResponse.list(orderService.getOwnerList(ownerHash, location, orderType, ownerAcceptStatus)));
    }
}
