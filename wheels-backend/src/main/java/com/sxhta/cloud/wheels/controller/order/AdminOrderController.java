package com.sxhta.cloud.wheels.controller.order;

import com.sxhta.cloud.common.web.page.PageRequest;
import com.sxhta.cloud.common.web.page.TableDataInfo;
import com.sxhta.cloud.wheels.remote.request.order.OrderSearchRequest;
import com.sxhta.cloud.wheels.remote.response.order.OrderAdminResponse;
import com.sxhta.cloud.wheels.service.order.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    public TableDataInfo<OrderAdminResponse> getBackstageList(@ModelAttribute("OrderSearchRequest") OrderSearchRequest request, PageRequest pageRequest) throws ParseException{
        return orderService.getBackstageList(request,pageRequest);
    }
}
