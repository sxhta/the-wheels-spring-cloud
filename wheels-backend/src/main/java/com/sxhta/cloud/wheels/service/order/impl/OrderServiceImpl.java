package com.sxhta.cloud.wheels.service.order.impl;

import com.sxhta.cloud.common.constant.SecurityConstants;
import com.sxhta.cloud.common.web.domain.CommonResponse;
import com.sxhta.cloud.common.web.page.PageRequest;
import com.sxhta.cloud.common.web.page.TableDataInfo;
import com.sxhta.cloud.wheels.remote.openfeign.order.OrderOpenfeign;
import com.sxhta.cloud.wheels.remote.request.order.OrderSearchRequest;
import com.sxhta.cloud.wheels.remote.response.order.OrderAdminInfoResponse;
import com.sxhta.cloud.wheels.remote.response.order.OrderAdminResponse;
import com.sxhta.cloud.wheels.service.order.OrderService;
import jakarta.inject.Inject;
import org.springframework.stereotype.Service;

import java.io.Serial;
import java.io.Serializable;
import java.text.ParseException;

@Service
public class OrderServiceImpl implements OrderService , Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Inject
    private OrderOpenfeign orderOpenfeign;

    @Override
    public TableDataInfo<OrderAdminResponse> getBackstageList(OrderSearchRequest request, PageRequest pageRequest) throws ParseException {
        final var resData = orderOpenfeign.getBackstageList(request, pageRequest, SecurityConstants.INNER);
        return resData.getData();
    }

    @Override
    public OrderAdminInfoResponse getBackstageInfo(String orderHash) {
        final var resData = orderOpenfeign.getBackstageInfo(orderHash, SecurityConstants.INNER);
        return resData.getData();
    }
}
