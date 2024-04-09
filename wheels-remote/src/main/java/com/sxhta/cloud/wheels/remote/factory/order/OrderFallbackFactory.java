package com.sxhta.cloud.wheels.remote.factory.order;

import com.sxhta.cloud.common.web.domain.CommonResponse;
import com.sxhta.cloud.common.web.page.PageRequest;
import com.sxhta.cloud.common.web.page.TableDataInfo;
import com.sxhta.cloud.wheels.remote.openfeign.order.OrderOpenfeign;
import com.sxhta.cloud.wheels.remote.response.order.OrderResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;

import java.io.Serial;
import java.io.Serializable;

/**
 * 用户服务降级处理
 */
@Component
public class OrderFallbackFactory implements FallbackFactory<OrderOpenfeign>, Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private final Logger logger = LoggerFactory.getLogger(OrderFallbackFactory.class);

    @Override
    public OrderOpenfeign create(Throwable throwable) {
        logger.error("订单服务调用失败:{}", throwable.getMessage());
        return new OrderOpenfeign() {
            @Override
            public CommonResponse<TableDataInfo<OrderResponse>> getFrontList(String userHash, Integer type, PageRequest pageRequest, String source) {
                return CommonResponse.error("订单获取失败:" + throwable.getMessage());
            }
        };
    }
}
