package com.wheels.cloud.frontend.service.order;

import com.sxhta.cloud.common.web.page.PageRequest;
import com.sxhta.cloud.common.web.page.TableDataInfo;
import com.sxhta.cloud.wheels.remote.response.order.admin.OrderExpectationResponse;
import com.sxhta.cloud.wheels.remote.response.order.front.OrderInfoResponse;
import com.sxhta.cloud.wheels.remote.response.order.front.OrderResponse;
import com.sxhta.cloud.wheels.remote.response.order.owner.OrderOwnerResponse;

public interface OrderService {
    TableDataInfo<OrderResponse> getFrontList(Integer type, PageRequest pageRequest);

    OrderInfoResponse getFrontInfo(String orderHash);

    Double getFrontTotalMileage();

    TableDataInfo<OrderExpectationResponse> getFrontExpectationList(PageRequest pageRequest);

    TableDataInfo<OrderOwnerResponse> getOwnerList(Integer location, Integer orderType,Integer ownerAcceptStatus,  PageRequest pageRequest);
}
