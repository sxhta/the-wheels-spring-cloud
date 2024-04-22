package com.sxhta.cloud.wheels.order.service;

import com.sxhta.cloud.common.service.ICommonService;
import com.sxhta.cloud.wheels.order.request.OrderInfoRequest;
import com.sxhta.cloud.wheels.order.request.OrderInfoSearchRequest;
import com.sxhta.cloud.wheels.order.response.InfoResponse;
import com.sxhta.cloud.wheels.remote.response.order.front.OrderInfoResponse;

/**
 * 订单详情 服务类
 */
public interface OrderInfoService extends ICommonService<OrderInfoSearchRequest, OrderInfoRequest, OrderInfoResponse> {

    InfoResponse getInfoByOrderHash(String orderHash);

}
