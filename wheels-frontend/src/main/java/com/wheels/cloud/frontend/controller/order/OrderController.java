package com.wheels.cloud.frontend.controller.order;

import com.sxhta.cloud.common.web.page.PageRequest;
import com.sxhta.cloud.common.web.page.TableDataInfo;
import com.sxhta.cloud.wheels.remote.response.order.OrderResponse;
import com.wheels.cloud.frontend.service.order.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
        System.out.println("111111111111");
        return orderService.getFrontList(type,pageRequest);
    }
}