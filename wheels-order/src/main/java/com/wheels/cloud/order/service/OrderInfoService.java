package com.wheels.cloud.order.service;

import com.sxhta.cloud.common.service.ICommonService;
import com.wheels.cloud.order.entity.OrderInfo;
import com.baomidou.mybatisplus.extension.service.IService;
import com.wheels.cloud.order.request.OrderInfoRequest;
import com.wheels.cloud.order.response.OrderInfoResponse;
import com.wheels.cloud.order.request.OrderInfoSearchRequest;
/**
 * 订单详情 服务类
 */
public interface OrderInfoService extends ICommonService<OrderInfoSearchRequest, OrderInfoRequest,OrderInfoResponse> {

    OrderInfoResponse getInfoByOrderHash(String orderHash);

}
