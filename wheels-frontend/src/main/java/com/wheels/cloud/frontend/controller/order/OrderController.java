package com.wheels.cloud.frontend.controller.order;

import com.sxhta.cloud.common.constant.SecurityConstants;
import com.sxhta.cloud.common.web.domain.CommonResponse;
import com.sxhta.cloud.common.web.page.PageRequest;
import com.sxhta.cloud.common.web.page.TableDataInfo;
import com.sxhta.cloud.wheels.remote.response.order.OrderExpectationResponse;
import com.sxhta.cloud.wheels.remote.response.order.OrderInfoResponse;
import com.sxhta.cloud.wheels.remote.response.order.OrderResponse;
import com.wheels.cloud.frontend.service.order.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.io.Serial;
import java.io.Serializable;

@Slf4j
@RestController
@RequestMapping("/front/order")
public class OrderController implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Inject
    private OrderService orderService;

    @GetMapping("/list")
    @Operation(summary = "客户端列表")
    public TableDataInfo<OrderResponse> getFrontList(@RequestParam(value = "type",defaultValue = "") Integer type,//1已完成，2已取消
                                                                     PageRequest pageRequest) {
        return orderService.getFrontList(type,pageRequest);
    }
    @GetMapping("/info")
    @Operation(summary = "客户端详情")
    public CommonResponse<OrderInfoResponse> getFrontInfo(@RequestParam(value = "orderHash") String orderHash) {
        return CommonResponse.success(orderService.getFrontInfo(orderHash));
    }

    @Operation(summary = "客户端待出行列表")
    @GetMapping("/expectation/list")
    CommonResponse<TableDataInfo<OrderExpectationResponse>> getFrontExpectationList(PageRequest pageRequest){
        return CommonResponse.success(orderService.getFrontExpectationList(pageRequest));
    }

    @Operation(summary = "客户端总里程")
    @GetMapping("/total/mileage")
    public CommonResponse<Double> getFrontTotalMileage(){
        return CommonResponse.success(orderService.getFrontTotalMileage());
    }
}