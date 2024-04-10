package com.sxhta.cloud.wheels.remote.openfeign.order;

import com.sxhta.cloud.common.constant.SecurityConstants;
import com.sxhta.cloud.common.constant.ServiceNameConstants;
import com.sxhta.cloud.common.web.domain.CommonResponse;
import com.sxhta.cloud.common.web.page.PageRequest;
import com.sxhta.cloud.common.web.page.TableDataInfo;
import com.sxhta.cloud.wheels.remote.factory.order.OrderFallbackFactory;
import com.sxhta.cloud.wheels.remote.request.order.OrderSearchRequest;
import com.sxhta.cloud.wheels.remote.response.order.OrderAdminResponse;
import com.sxhta.cloud.wheels.remote.response.order.OrderExpectationResponse;
import com.sxhta.cloud.wheels.remote.response.order.OrderInfoResponse;
import com.sxhta.cloud.wheels.remote.response.order.OrderResponse;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;

@FeignClient(contextId = "orderOpenFeign", value = ServiceNameConstants.WHEELS_ORDER, fallbackFactory = OrderFallbackFactory.class)
public interface OrderOpenfeign {

    /**
     * 对象参数在FeignClient里，必须用 @SpringQueryMap 或 @RequestParam标记
     */
    @Operation(summary = "客户端列表")
    @GetMapping("/orders/user/front/list")
    CommonResponse<TableDataInfo<OrderResponse>> getFrontList(@RequestParam(value = "userHash") String userHash, @RequestParam(value = "type", defaultValue = "") Integer type,//1已完成，2已取消
                                                              @RequestParam PageRequest pageRequest, @RequestHeader(SecurityConstants.FROM_SOURCE) String source);

    @Operation(summary = "客户端详情")
    @GetMapping("/orders/user/front/info/{orderHash}")
    CommonResponse<OrderInfoResponse> getFrontInfo(@PathVariable(value = "orderHash") String orderHash, @RequestHeader(SecurityConstants.FROM_SOURCE) String source);

    @Operation(summary = "客户端待出行列表")
    @GetMapping("/orders/user/front/expectation/list")
    CommonResponse<TableDataInfo<OrderExpectationResponse>> getFrontExpectationList(@RequestParam(value = "userHash") String userHash,
                                                                                    @RequestParam PageRequest pageRequest,
                                                                                    @RequestHeader(SecurityConstants.FROM_SOURCE) String source);

    @Operation(summary = "客户端总里程")
    @GetMapping("/orders/user/front/total/mileage")
    CommonResponse<Double> getFrontTotalMileage(@RequestParam(value = "userHash") String userHash, @RequestHeader(SecurityConstants.FROM_SOURCE) String source);

    @Operation(summary = "后管订单列表")
    @GetMapping("/orders/admin/list")
    CommonResponse<TableDataInfo<OrderAdminResponse>> getBackstageList(@RequestPart("request")  OrderSearchRequest request, @RequestParam PageRequest pageRequest, @RequestHeader(SecurityConstants.FROM_SOURCE) String source) throws ParseException;
}
