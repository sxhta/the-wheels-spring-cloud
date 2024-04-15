package com.wheels.cloud.frontend.controller.owner.order;

import com.sxhta.cloud.common.web.page.PageRequest;
import com.sxhta.cloud.common.web.page.TableDataInfo;
import com.sxhta.cloud.wheels.remote.response.order.owner.OrderOwnerResponse;
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
@RequestMapping("/owner/order")
public class OwnerOrderController implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Inject
    private OrderService orderService;

    @Operation(summary = "司机端列表")
    @GetMapping("/list")
    public TableDataInfo<OrderOwnerResponse> getOwnerList(@RequestParam(value = "location") Integer location,//1首页，2全部
                                                          @RequestParam(value = "orderType") Integer orderType,//1即时订单，2预约订单
                                                          @RequestParam(value = "ownerAcceptStatus") Integer ownerAcceptStatus, //2可接单，3成功接单，
                                                          PageRequest pageRequest) {
        return orderService.getOwnerList(location,orderType,ownerAcceptStatus,pageRequest);
    }

}
