package com.sxhta.cloud.wheels.order.service;

import com.sxhta.cloud.common.service.ICommonService;
import com.sxhta.cloud.remote.domain.SysFile;
import com.sxhta.cloud.wheels.order.request.OrderRequest;
import com.sxhta.cloud.wheels.remote.response.order.*;
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

    OrderAdminInfoResponse getBackstageInfo(String orderHash);

    SysFile getBackstageExport(OrderSearchRequest request) throws ParseException;
}
