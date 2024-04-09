package com.wheels.cloud.order.service;

import com.sxhta.cloud.common.service.ICommonService;
import com.sxhta.cloud.wheels.remote.response.OrderResponse;
import com.wheels.cloud.order.request.OrderRequest;
import com.wheels.cloud.order.request.OrderSearchRequest;

import java.util.List;

/**
 * 订单管理 服务类
 */
public interface OrderService extends ICommonService<OrderSearchRequest, OrderRequest, OrderResponse> {

    List<OrderResponse> getFrontList(String userHash,Integer type);
}
