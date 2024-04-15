package com.sxhta.cloud.wheels.remote.factory.order;

import com.sxhta.cloud.common.web.domain.CommonResponse;
import com.sxhta.cloud.common.web.page.PageRequest;
import com.sxhta.cloud.common.web.page.TableDataInfo;
import com.sxhta.cloud.wheels.remote.domain.SysFile;
import com.sxhta.cloud.wheels.remote.openfeign.order.OrderOpenfeign;
import com.sxhta.cloud.wheels.remote.request.order.OrderSearchRequest;
import com.sxhta.cloud.wheels.remote.response.order.admin.OrderAdminInfoResponse;
import com.sxhta.cloud.wheels.remote.response.order.admin.OrderAdminResponse;
import com.sxhta.cloud.wheels.remote.response.order.admin.OrderExpectationResponse;
import com.sxhta.cloud.wheels.remote.response.order.front.OrderInfoResponse;
import com.sxhta.cloud.wheels.remote.response.order.front.OrderResponse;
import com.sxhta.cloud.wheels.remote.response.order.owner.OrderOwnerResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;

import java.io.Serial;
import java.io.Serializable;
import java.text.ParseException;

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

            @Override
            public CommonResponse<OrderInfoResponse> getFrontInfo(String orderHash, String source) {
                return CommonResponse.error("订单详情获取失败:" + throwable.getMessage());
            }

            @Override
            public CommonResponse<TableDataInfo<OrderExpectationResponse>> getFrontExpectationList(String userHash, PageRequest pageRequest, String source) {
                return CommonResponse.error("待出行列表获取失败:" + throwable.getMessage());
            }

            @Override
            public CommonResponse<Double> getFrontTotalMileage(String userHash, String source) {
                return CommonResponse.error("总里程数获取失败:" + throwable.getMessage());
            }

            @Override
            public CommonResponse<TableDataInfo<OrderAdminResponse>> getBackstageList(OrderSearchRequest request, PageRequest pageRequest, String source) throws ParseException {
                return CommonResponse.error("订单列表获取失败:" + throwable.getMessage());
            }

            @Override
            public CommonResponse<OrderAdminInfoResponse> getBackstageInfo(String orderHash, String source) {
                return CommonResponse.error("订单详情获取失败:" + throwable.getMessage());
            }

            @Override
            public CommonResponse<SysFile> getBackstageExport(OrderSearchRequest request, String source) {
                return CommonResponse.error("订单导出失败:" + throwable.getMessage());
            }

            @Override
            public CommonResponse<TableDataInfo<OrderOwnerResponse>> getOwnerList(String ownerHash, Integer location, Integer orderType, PageRequest pageRequest, String source) {
                return CommonResponse.error("接单列表获取失败:" + throwable.getMessage());
            }
        };
    }
}
