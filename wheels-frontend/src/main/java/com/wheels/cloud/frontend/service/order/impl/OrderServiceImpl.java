package com.wheels.cloud.frontend.service.order.impl;

import cn.hutool.core.util.ObjectUtil;
import com.sxhta.cloud.common.constant.SecurityConstants;
import com.sxhta.cloud.common.exception.CommonNullException;
import com.sxhta.cloud.common.web.page.PageRequest;
import com.sxhta.cloud.common.web.page.TableDataInfo;
import com.sxhta.cloud.security.service.TokenService;
import com.sxhta.cloud.wheels.remote.domain.user.WheelsFrontUser;
import com.sxhta.cloud.wheels.remote.openfeign.order.OrderOpenfeign;
import com.sxhta.cloud.wheels.remote.response.order.OrderInfoResponse;
import com.sxhta.cloud.wheels.remote.response.order.OrderResponse;
import com.sxhta.cloud.wheels.remote.vo.FrontUserCacheVo;
import com.wheels.cloud.frontend.service.order.OrderService;
import com.wheels.cloud.frontend.service.user.FrontUserService;
import jakarta.inject.Inject;
import org.springframework.stereotype.Service;

import java.io.Serial;
import java.io.Serializable;

@Service
public class OrderServiceImpl implements OrderService, Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Inject
    private TokenService<FrontUserCacheVo, WheelsFrontUser> tokenService;

    @Inject
    private OrderOpenfeign orderOpenfeign;

    @Inject
    private FrontUserService frontUserService;

    @Override
    public TableDataInfo<OrderResponse> getFrontList(Integer type, PageRequest pageRequest) {
//        final var frontUserHash = frontUserService.getHashById(tokenService.getLoginUser().getUserid());
        //TODO:获取用户HASH
        final var orderOpenfeignFrontList = orderOpenfeign.getFrontList("6007f67fd63df554b919991d89c6a07f5ca57044d3e97a9e108fd2e567989021", type, pageRequest, SecurityConstants.INNER);
        return orderOpenfeignFrontList.getData();
    }

    @Override
    public OrderInfoResponse getFrontInfo(String orderHash) {
        final var orderInfo = orderOpenfeign.getFrontInfo(orderHash,SecurityConstants.INNER);
        if (orderInfo.getData().getIsUrgent() == 2) {
            final var orderInfoResponse = orderInfo.getData();
            final var frontUser = frontUserService.getById(tokenService.getUserId());
            if (ObjectUtil.isNull(frontUser)) {
                throw new CommonNullException("用户不存在！");
            }
            //TODO：用户性别
            orderInfoResponse.setSex("先生");
            orderInfoResponse.setUserPhone(frontUser.getAccount());
            orderInfoResponse.setUserName(frontUser.getUserName());
        }
        return orderInfo.getData();
    }
}
