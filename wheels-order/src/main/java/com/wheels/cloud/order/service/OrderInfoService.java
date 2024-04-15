package com.wheels.cloud.order.service;

import com.sxhta.cloud.common.service.ICommonService;
import com.sxhta.cloud.wheels.remote.response.order.front.OrderInfoResponse;
import com.wheels.cloud.order.request.OrderInfoRequest;
import com.wheels.cloud.order.request.OrderInfoSearchRequest;
import com.wheels.cloud.order.response.InfoResponse;

/**
 * 订单详情 服务类
 */
public interface OrderInfoService extends ICommonService<OrderInfoSearchRequest, OrderInfoRequest, OrderInfoResponse> {

    InfoResponse getInfoByOrderHash(String orderHash);

}
