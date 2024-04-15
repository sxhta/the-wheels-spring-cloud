package com.wheels.cloud.frontend.service.order.impl;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.sxhta.cloud.common.constant.SecurityConstants;
import com.sxhta.cloud.common.exception.CommonNullException;
import com.sxhta.cloud.common.web.page.PageRequest;
import com.sxhta.cloud.common.web.page.TableDataInfo;
import com.sxhta.cloud.security.service.TokenService;
import com.sxhta.cloud.wheels.remote.domain.user.WheelsFrontUser;
import com.sxhta.cloud.wheels.remote.openfeign.order.OrderOpenfeign;
import com.sxhta.cloud.wheels.remote.response.order.admin.OrderExpectationResponse;
import com.sxhta.cloud.wheels.remote.response.order.front.OrderInfoResponse;
import com.sxhta.cloud.wheels.remote.response.order.front.OrderResponse;
import com.sxhta.cloud.wheels.remote.response.order.owner.OrderOwnerResponse;
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
        final var orderOpenfeignFrontList = orderOpenfeign.getFrontList(frontUserService.getHasHById(tokenService.getUserId()), type, pageRequest, SecurityConstants.INNER);
        return orderOpenfeignFrontList.getData();
    }

    @Override
    public OrderInfoResponse getFrontInfo(String orderHash) {
        final var orderInfo = orderOpenfeign.getFrontInfo(orderHash, SecurityConstants.INNER);
        if (ObjectUtil.isNull(orderInfo.getData().getSex()) &&
                StrUtil.isBlank(orderInfo.getData().getUserName()) &&
                StrUtil.isBlank(orderInfo.getData().getUserPhone())) {
            final var orderInfoResponse = orderInfo.getData();
            final var userLqw = new LambdaQueryWrapper<WheelsFrontUser>();
            userLqw.eq(WheelsFrontUser::getUserId,tokenService.getUserId());
            final var frontUser = frontUserService.getOne(userLqw);
            if (ObjectUtil.isNull(frontUser)) {
                throw new CommonNullException("用户不存在！");
            }
            orderInfoResponse.setSex(frontUser.getGender());
            orderInfoResponse.setUserPhone(frontUser.getAccount());
            orderInfoResponse.setUserName(frontUser.getUserName());
        }
        return orderInfo.getData();
    }

    @Override
    public Double getFrontTotalMileage() {
        final var res = orderOpenfeign.getFrontTotalMileage(frontUserService.getHasHById(tokenService.getUserId()), SecurityConstants.INNER);
        return res.getData();
    }

    @Override
    public TableDataInfo<OrderExpectationResponse> getFrontExpectationList(PageRequest pageRequest) {
        final var orderFrontExpectationList = orderOpenfeign.getFrontExpectationList(frontUserService.getHasHById(tokenService.getUserId()), pageRequest, SecurityConstants.INNER);
        orderFrontExpectationList.getData().getRows().forEach(order -> {
            if (ObjectUtil.isNull(order.getSex()) &&
                    StrUtil.isBlank(order.getUserName()) &&
                    StrUtil.isBlank(order.getUserPhone())) {
                final var frontUserCacheVo = tokenService.getLoginUser();
                final var frontUserInCache = frontUserCacheVo.getUserEntity();
                final var frontUserId = frontUserInCache.getUserId();
                final var userLqw = new LambdaQueryWrapper<WheelsFrontUser>();
                userLqw.eq(WheelsFrontUser::getUserId,frontUserId);
                final var frontUser = frontUserService.getOne(userLqw);
                if (ObjectUtil.isNull(frontUser)) {
                    throw new CommonNullException("用户不存在！");
                }
                order.setSex(frontUser.getGender());
                order.setUserPhone(frontUser.getAccount());
                order.setUserName(frontUser.getUserName());
            }
        });
        return orderFrontExpectationList.getData();
    }

    @Override
    public TableDataInfo<OrderOwnerResponse> getOwnerList(Integer location, Integer orderType,Integer ownerAcceptStatus,  PageRequest pageRequest) {
        //TODO:司机登录HASH
        final var resData = orderOpenfeign.getOwnerList("a1", location, orderType, ownerAcceptStatus,pageRequest, SecurityConstants.INNER);
        return resData.getData();
    }
}
