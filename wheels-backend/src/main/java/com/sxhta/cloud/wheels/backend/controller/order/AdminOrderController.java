package com.sxhta.cloud.wheels.backend.controller.order;

import com.sxhta.cloud.common.web.domain.CommonResponse;
import com.sxhta.cloud.common.web.page.PageRequest;
import com.sxhta.cloud.common.web.page.TableDataInfo;
import com.sxhta.cloud.wheels.remote.domain.SysFile;
import com.sxhta.cloud.wheels.remote.request.order.OrderSearchRequest;
import com.sxhta.cloud.wheels.remote.response.order.OrderAdminInfoResponse;
import com.sxhta.cloud.wheels.remote.response.order.OrderAdminResponse;
import com.sxhta.cloud.wheels.backend.service.order.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.openfeign.SpringQueryMap;
import org.springframework.web.bind.annotation.*;

import java.io.Serial;
import java.io.Serializable;
import java.text.ParseException;

@Slf4j
@RestController
@RequestMapping("/admin/order")
public class AdminOrderController implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Inject
    private OrderService orderService;

    @Operation(summary = "后管订单列表")
    @GetMapping("/list")
    public TableDataInfo<OrderAdminResponse> getBackstageList(@ModelAttribute("OrderSearchRequest") OrderSearchRequest request, PageRequest pageRequest) throws ParseException {
        return orderService.getBackstageList(request, pageRequest);
    }

    @Operation(summary = "后管订单详情")
    @GetMapping("/info")
    public CommonResponse<OrderAdminInfoResponse> getBackstageInfo(@RequestParam("orderHash") String orderHash) {
        return CommonResponse.success(orderService.getBackstageInfo(orderHash));
    }

    @Operation(summary = "后管订单导出")
    @GetMapping("/export")
    public CommonResponse<SysFile> getBackstageExport(@SpringQueryMap OrderSearchRequest request) throws ParseException {
        return CommonResponse.success(orderService.getBackstageExport(request));
    }
}
