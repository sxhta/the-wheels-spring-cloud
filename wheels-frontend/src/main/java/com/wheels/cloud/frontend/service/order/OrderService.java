package com.wheels.cloud.frontend.service.order;

import com.sxhta.cloud.common.web.page.PageRequest;
import com.sxhta.cloud.common.web.page.TableDataInfo;
import com.sxhta.cloud.wheels.remote.response.order.OrderExpectationResponse;
import com.sxhta.cloud.wheels.remote.response.order.OrderInfoResponse;
import com.sxhta.cloud.wheels.remote.response.order.OrderResponse;

public interface OrderService {
    TableDataInfo<OrderResponse> getFrontList(Integer type, PageRequest pageRequest);

    OrderInfoResponse getFrontInfo(String orderHash);

    Double getFrontTotalMileage();

    TableDataInfo<OrderExpectationResponse> getFrontExpectationList(PageRequest pageRequest);
}
