package com.wheels.cloud.order.service;

import com.sxhta.cloud.common.service.ICommonService;
import com.sxhta.cloud.wheels.remote.response.order.OrderAdminResponse;
import com.sxhta.cloud.wheels.remote.response.order.OrderExpectationResponse;
import com.sxhta.cloud.wheels.remote.response.order.OrderInfoResponse;
import com.sxhta.cloud.wheels.remote.response.order.OrderResponse;
import com.wheels.cloud.order.request.OrderRequest;
import com.sxhta.cloud.wheels.remote.request.order.OrderSearchRequest;

import java.text.ParseException;
import java.util.List;

/**
 * 订单管理 服务类
 */
public interface OrderService extends ICommonService<OrderSearchRequest, OrderRequest, OrderResponse> {

    List<OrderResponse> getFrontList(String userHash,Integer type);

    OrderInfoResponse getFrontInfo(String orderHash);

    List<OrderExpectationResponse> getFrontExpectationList(String userHash);

    Double getFrontTotalMileage(String userHash);

    List<OrderAdminResponse> getBackstageList(OrderSearchRequest request) throws ParseException;
}
