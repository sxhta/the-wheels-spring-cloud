package com.wheels.cloud.order.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sxhta.cloud.common.exception.CommonNullException;
import com.sxhta.cloud.security.service.TokenService;
import com.sxhta.cloud.wheels.remote.domain.order.Order;
import com.sxhta.cloud.wheels.remote.domain.user.WheelsFrontUser;
import com.sxhta.cloud.wheels.remote.openfeign.user.FrontUserOpenFeign;
import com.sxhta.cloud.wheels.remote.response.OrderResponse;
import com.sxhta.cloud.wheels.remote.vo.FrontUserCacheVo;
import com.wheels.cloud.order.mapper.OrderMapper;
import com.wheels.cloud.order.request.OrderRequest;
import com.wheels.cloud.order.request.OrderSearchRequest;
import com.wheels.cloud.order.service.OrderInfoService;
import com.wheels.cloud.order.service.OrderService;
import jakarta.inject.Inject;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * 订单管理 服务实现类
 */
@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order> implements OrderService, Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Inject
    private TokenService<FrontUserCacheVo, WheelsFrontUser> tokenService;

    @Inject
    private FrontUserOpenFeign frontUserOpenFeign;

    @Inject
    private OrderInfoService orderInfoService;


    @Override
    public Boolean create(OrderRequest request) {
        final var entity = new Order();
        BeanUtils.copyProperties(request, entity);
        final var loginUser = tokenService.getLoginUser();
        final var createBy = loginUser.getUsername();
        entity.setCreateBy(createBy);
        return save(entity);
    }

    @Override
    public OrderResponse getInfoByHash(String hash) {
        final var lqw = new LambdaQueryWrapper<Order>();
        lqw.eq(Order::getHash, hash);
        final var entity = getOne(lqw);
        if (ObjectUtil.isNull(entity)) {
            throw new CommonNullException("");
        }
        final var response = new OrderResponse();
        BeanUtils.copyProperties(entity, response);
        return response;
    }

    @Override
    public Boolean softDeleteByHash(String hash) {
        final var lqw = new LambdaQueryWrapper<Order>();
        lqw.eq(Order::getHash, hash);
        final var entity = getOne(lqw);
        if (ObjectUtil.isNull(entity)) {
            throw new CommonNullException("");
        }
        entity.setDeleteTime(LocalDateTime.now());
        return updateById(entity);
    }

    @Override
    public Boolean deleteByHash(String hash) {
        final var lqw = new LambdaQueryWrapper<Order>();
        lqw.eq(Order::getHash, hash);
        final var entity = getOne(lqw);
        if (ObjectUtil.isNull(entity)) {
            throw new CommonNullException("");
        }
        return removeById(entity);
    }

    @Override
    public Boolean updateEntity(OrderRequest request) {
        final var hash = request.getHash();
        final var lqw = new LambdaQueryWrapper<Order>();
        lqw.eq(Order::getHash, hash);
        final var entity = getOne(lqw);
        if (ObjectUtil.isNull(entity)) {
            throw new CommonNullException("");
        }
        BeanUtils.copyProperties(request, entity);
        return updateById(entity);
    }

    @Override
    public List<OrderResponse> getAdminList(OrderSearchRequest request) {
        final var lqw = new LambdaQueryWrapper<Order>();
        lqw.isNull(Order::getDeleteTime);

        final var entityList = list(lqw);
        final var responseList = new ArrayList<OrderResponse>();
        entityList.forEach(entity -> {
            final var response = new OrderResponse();
            BeanUtils.copyProperties(entity, response);
            responseList.add(response);
        });
        return responseList;
    }

    @Override
    public List<OrderResponse> getFrontList(Integer type) {//1已完成，2已取消
        final var lqw = new LambdaQueryWrapper<Order>();
        final var frontUserHash = frontUserOpenFeign.getHashById(tokenService.getLoginUser().getUserid());
        lqw.and(i -> i.isNull(Order::getDeleteTime))
            .and(i->i.eq(Order::getUserHash,frontUserHash.getData().getHash()));
        if (ObjectUtil.isNotNull(type)) {
            if (type ==1){
                lqw.and(i -> i.eq(Order::getOrderStatus,4));
            }
            if (type ==2){
                lqw.and(i -> i.eq(Order::getOrderStatus,5));
            }
        }
        lqw.and(i -> i.orderByDesc(Order::getCreateTime));
        final var orderList = list(lqw);
        final var responseList = new ArrayList<OrderResponse>();
        if (CollUtil.isEmpty(orderList)) {
            return responseList;
        }
        orderList.forEach(order->{
            final var response = new OrderResponse();
            final var orderInfo = orderInfoService.getInfoByOrderHash(order.getHash());
            BeanUtils.copyProperties(orderInfo,response);
            response.setId(order.getId());
            response.setHash(order.getHash());
            //TODO: 车辆类型
            response.setCarType("车辆类型未写，请注意提醒！");
            responseList.add(response);
        });
        return responseList;
    }
}
