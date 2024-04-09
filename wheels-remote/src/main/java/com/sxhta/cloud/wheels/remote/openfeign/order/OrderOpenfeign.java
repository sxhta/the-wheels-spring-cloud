package com.sxhta.cloud.wheels.remote.openfeign.order;

import com.sxhta.cloud.common.constant.SecurityConstants;
import com.sxhta.cloud.common.constant.ServiceNameConstants;
import com.sxhta.cloud.common.web.domain.CommonResponse;
import com.sxhta.cloud.common.web.page.PageRequest;
import com.sxhta.cloud.common.web.page.TableDataInfo;
import com.sxhta.cloud.wheels.remote.factory.order.OrderFallbackFactory;
import com.sxhta.cloud.wheels.remote.response.order.OrderResponse;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(contextId = "orderOpenFeign", value = ServiceNameConstants.WHEELS_ORDER, fallbackFactory = OrderFallbackFactory.class)
public interface OrderOpenfeign {

    @GetMapping("/order/front/list")
    @Operation(summary = "客户端列表")
    CommonResponse<TableDataInfo<OrderResponse>> getFrontList(@RequestParam(value = "userHash") String userHash, @RequestParam(value = "type",defaultValue = "") Integer type,//1已完成，2已取消
                                                                     PageRequest pageRequest ,@RequestHeader(SecurityConstants.FROM_SOURCE) String source) ;
}
