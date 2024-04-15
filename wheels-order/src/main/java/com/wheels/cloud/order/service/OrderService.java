package com.wheels.cloud.order.service;

import com.sxhta.cloud.common.service.ICommonService;
import com.sxhta.cloud.remote.domain.SysFile;
import com.sxhta.cloud.wheels.remote.response.order.admin.OrderAdminInfoResponse;
import com.sxhta.cloud.wheels.remote.response.order.admin.OrderAdminResponse;
import com.sxhta.cloud.wheels.remote.response.order.admin.OrderExpectationResponse;
import com.sxhta.cloud.wheels.remote.response.order.front.OrderInfoResponse;
import com.sxhta.cloud.wheels.remote.response.order.front.OrderResponse;
import com.sxhta.cloud.wheels.remote.response.order.owner.OrderOwnerResponse;
import com.wheels.cloud.order.request.OrderRequest;
import com.sxhta.cloud.wheels.remote.request.order.OrderSearchRequest;

import java.text.ParseException;
import java.util.List;

/**
 * 订单管理 服务类
 */
public interface OrderService extends ICommonService<OrderSearchRequest, OrderRequest, OrderResponse> {

    List<OrderResponse> getFrontList(String userHash, Integer type);

    OrderInfoResponse getFrontInfo(String orderHash);

    List<OrderExpectationResponse> getFrontExpectationList(String userHash);

    Double getFrontTotalMileage(String userHash);

    List<OrderAdminResponse> getBackstageList(OrderSearchRequest request) throws ParseException;

    OrderAdminInfoResponse getBackstageInfo(String orderHash);

    SysFile getBackstageExport(OrderSearchRequest request) throws ParseException;

    List<OrderOwnerResponse> getOwnerList(String ownerHash, Integer location, Integer orderType);
}
