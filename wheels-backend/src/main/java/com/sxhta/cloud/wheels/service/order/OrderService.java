package com.sxhta.cloud.wheels.service.order;

import com.sxhta.cloud.common.web.page.PageRequest;
import com.sxhta.cloud.common.web.page.TableDataInfo;
import com.sxhta.cloud.wheels.remote.request.order.OrderSearchRequest;
import com.sxhta.cloud.wheels.remote.response.order.OrderAdminResponse;

import java.text.ParseException;

public interface OrderService {
    TableDataInfo<OrderAdminResponse> getBackstageList(OrderSearchRequest request, PageRequest pageRequest) throws ParseException;
}
