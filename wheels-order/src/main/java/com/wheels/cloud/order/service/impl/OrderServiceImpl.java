package com.wheels.cloud.order.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sxhta.cloud.common.constant.SecurityConstants;
import com.sxhta.cloud.common.exception.CommonNullException;
import com.sxhta.cloud.security.service.TokenService;
import com.sxhta.cloud.wheels.remote.domain.order.Order;
import com.sxhta.cloud.wheels.remote.domain.user.WheelsFrontUser;
import com.sxhta.cloud.wheels.remote.openfeign.user.FrontUserOpenFeign;
import com.sxhta.cloud.wheels.remote.response.order.OrderAdminResponse;
import com.sxhta.cloud.wheels.remote.response.order.OrderExpectationResponse;
import com.sxhta.cloud.wheels.remote.response.order.OrderInfoResponse;
import com.sxhta.cloud.wheels.remote.response.order.OrderResponse;
import com.sxhta.cloud.wheels.remote.vo.FrontUserCacheVo;
import com.wheels.cloud.order.mapper.OrderMapper;
import com.wheels.cloud.order.request.OrderRequest;
import com.sxhta.cloud.wheels.remote.request.order.OrderSearchRequest;
import com.wheels.cloud.order.service.OrderInfoService;
import com.wheels.cloud.order.service.OrderService;
import jakarta.inject.Inject;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

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
    private OrderInfoService orderInfoService;

    @Inject
    private FrontUserOpenFeign frontUserOpenFeign;

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
    public List<OrderResponse> getFrontList(String userHash, Integer type) {//1已完成，2已取消
        final var lqw = new LambdaQueryWrapper<Order>();
        lqw.and(i -> i.isNull(Order::getDeleteTime))
                .and(i -> i.eq(Order::getUserHash, userHash));
        if (ObjectUtil.isNotNull(type)) {
            if (type == 1) {
                lqw.and(i -> i.eq(Order::getOrderStatus, 4));
            }
            if (type == 2) {
                lqw.and(i -> i.eq(Order::getOrderStatus, 5));
            }
        }
        lqw.orderByDesc(Order::getCreateTime);
        final var orderList = list(lqw);
        final var responseList = new ArrayList<OrderResponse>();
        if (CollUtil.isEmpty(orderList)) {
            return responseList;
        }
        orderList.forEach(order -> {
            final var response = new OrderResponse();
            final var orderInfo = orderInfoService.getInfoByOrderHash(order.getHash());
            BeanUtils.copyProperties(orderInfo, response);
            response.setId(order.getId());
            response.setHash(order.getHash());
            response.setOrderStatus(order.getOrderStatus());
            response.setOrderType(order.getOrderType());
            response.setIsUrgent(order.getIsUrgent());
            response.setOrderAmount(order.getOrderAmount());
            //TODO: 车辆类型
            response.setCarType("车辆类型未写，请注意提醒！");
            responseList.add(response);
        });
        return responseList;
    }

    @Override
    public OrderInfoResponse getFrontInfo(String orderHash) {
        final var lqw = new LambdaQueryWrapper<Order>();
        lqw.and(i -> i.isNull(Order::getDeleteTime))
                .and(i -> i.eq(Order::getHash, orderHash));
        final var order = getOne(lqw);
        if (ObjectUtil.isNull(order)) {
            throw new CommonNullException("该订单不存在！");
        }
        final var orderInfo = orderInfoService.getInfoByOrderHash(order.getHash());
        final var response = new OrderInfoResponse();
        BeanUtils.copyProperties(order, response);
        BeanUtils.copyProperties(orderInfo, response);
        response.setId(order.getId());
        response.setHash(order.getHash());

        //计算其他费用
        final var allOtherAmountRef = new AtomicReference<BigDecimal>();//总金额
        allOtherAmountRef.set(BigDecimal.ZERO);//设置总金额
        if (ObjectUtil.isNotNull(order.getUrgentAmount())) {
            allOtherAmountRef.set(allOtherAmountRef.get().add(order.getUrgentAmount()));
        }
        if (ObjectUtil.isNotNull(order.getOtherFee())) {
            allOtherAmountRef.set(allOtherAmountRef.get().add(order.getOtherFee()));
        }
        response.setOtherFee(allOtherAmountRef.get());
        response.setOrderCreateTime(order.getCreateTime());
        //TODO:优惠卷金额
        response.setCouponAmount(new BigDecimal(BigInteger.ZERO));
        //TODO:车辆类型
        response.setCarType("车辆类型");
        if (orderInfo.getIsHelpCall() == 1) {
            response.setUserName(orderInfo.getHelpName());
            response.setUserPhone(orderInfo.getHelpPhone());
            response.setSex(orderInfo.getHelpSex());
        }
        return response;
    }

    @Override
    public List<OrderExpectationResponse> getFrontExpectationList(String userHash) {
        final var lqw = new LambdaQueryWrapper<Order>();
        lqw.and(i -> i.isNull(Order::getDeleteTime))
                .and(i -> i.eq(Order::getUserHash, userHash))
                .and(i -> i.in(Order::getOrderStatus, 2, 3));
        lqw.orderByDesc(Order::getOrderStatus);
        lqw.orderByDesc(Order::getAppointmentTime);
        final var orderList = list(lqw);
        final var responseList = new ArrayList<OrderExpectationResponse>();
        if (CollUtil.isEmpty(orderList)) {
            return responseList;
        }
        orderList.forEach(order -> {
            final var response = new OrderExpectationResponse();
            final var orderInfo = orderInfoService.getInfoByOrderHash(order.getHash());
            BeanUtils.copyProperties(order, response);
            BeanUtils.copyProperties(orderInfo, response);
            response.setId(order.getId());
            response.setHash(order.getHash());
            response.setAppointmentTime(order.getAppointmentTime());
            if (orderInfo.getIsHelpCall() == 1) {
                response.setUserName(orderInfo.getHelpName());
                response.setUserPhone(orderInfo.getHelpPhone());
                response.setSex(orderInfo.getHelpSex());
            }
            //TODO:车辆管理
            responseList.add(response);
        });
        return responseList;
    }

    @Override
    public Double getFrontTotalMileage(String userHash) {
        final var lqw = new LambdaQueryWrapper<Order>();
        lqw.and(i -> i.eq(Order::getUserHash, userHash));
        lqw.and(i -> i.in(Order::getOrderStatus, 4));
        final var orderList = list(lqw);
        if (CollUtil.isEmpty(orderList)) {
            return 0.00;
        }
        final var totalMileage = new AtomicReference<>(0.00);
        orderList.forEach(order -> {
            final var orderInfo = orderInfoService.getInfoByOrderHash(order.getHash());
            totalMileage.updateAndGet(v -> v + Double.parseDouble(orderInfo.getMileage()));
        });
        return totalMileage.get();
    }

    @Override
    public List<OrderAdminResponse> getBackstageList(OrderSearchRequest request) throws ParseException {
        final var lqw = new LambdaQueryWrapper<Order>();
        if (StrUtil.isNotBlank(request.getOrderNo())) {
            lqw.and(i -> i.like(Order::getOrderNo, request.getOrderNo()));
        }
        if (ObjectUtil.isNotNull(request.getOrderStatus())) {
            lqw.and(i -> i.in(Order::getOrderStatus, request.getOrderStatus()));
        }
        if (ObjectUtil.isNotNull(request.getIsUrgent())) {
            lqw.and(i -> i.in(Order::getIsUrgent, request.getIsUrgent()));
        }
        if (ObjectUtil.isNotNull(request.getOrderStatus())) {
            lqw.and(i -> i.in(Order::getOrderStatus, request.getOrderStatus()));
        }
        if (StrUtil.isNotBlank(request.getPlaceOrderUserName())) {
            final var resData = frontUserOpenFeign.getHashListByUserName(request.getPlaceOrderUserName(), SecurityConstants.INNER);
            if (CollUtil.isNotEmpty(resData.getData())) {
                lqw.and(i -> i.in(Order::getUserHash, resData.getData()));
            }
        }
        if (StrUtil.isNotBlank(request.getPlaceOrderUserPhone())) {
            final var resData = frontUserOpenFeign.getHashListByUserPhone(request.getPlaceOrderUserPhone(), SecurityConstants.INNER);
            if (CollUtil.isNotEmpty(resData.getData())) {
                lqw.and(i -> i.in(Order::getUserHash, resData.getData()));
            }
        }
        if (StrUtil.isNotBlank(request.getReservationStartTime()) && StrUtil.isNotBlank(request.getReservationEndTime())) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date dateStart = sdf.parse(request.getReservationStartTime());
            Date dateEnd = sdf.parse(request.getReservationEndTime());
            lqw.and(consumer -> consumer.between(Order::getAppointmentTime, dateStart, dateEnd));
        }
        if (StrUtil.isNotBlank(request.getCreateStartTime()) && StrUtil.isNotBlank(request.getCreateEndTime())) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date dateStart = sdf.parse(request.getCreateStartTime());
            Date dateEnd = sdf.parse(request.getCreateEndTime());
            lqw.and(consumer -> consumer.between(Order::getCreateTime, dateStart, dateEnd));
        }
        lqw.orderByDesc(Order::getCreateTime);
        final var orderList = list(lqw);
        final var responseList = new ArrayList<OrderAdminResponse>();
        if (CollUtil.isEmpty(orderList)) {
            return responseList;
        }
        orderList.forEach(order -> {
            final var response = new OrderAdminResponse();
            BeanUtils.copyProperties(order,response);
            final var orderInfo = orderInfoService.getInfoByOrderHash(order.getHash());
            response.setDeparture(orderInfo.getDeparture());
            response.setDestination(orderInfo.getDestination());
            final var resData = frontUserOpenFeign.getInfoByHash(order.getUserHash(), SecurityConstants.INNER);
            response.setPlaceOrderUserName(resData.getData().getUserName());
            response.setPlaceOrderUserPhone(resData.getData().getAccount());
            responseList.add(response);
        });
        return responseList;
    }
}
