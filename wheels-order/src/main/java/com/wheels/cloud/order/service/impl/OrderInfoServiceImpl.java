package com.wheels.cloud.order.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sxhta.cloud.common.exception.CommonException;
import com.sxhta.cloud.common.exception.CommonNullException;
import com.sxhta.cloud.remote.domain.SysUser;
import com.sxhta.cloud.remote.vo.SystemUserCacheVo;
import com.sxhta.cloud.security.service.TokenService;
import com.wheels.cloud.order.entity.OrderInfo;
import com.wheels.cloud.order.mapper.OrderInfoMapper;
import com.wheels.cloud.order.request.OrderInfoRequest;
import com.wheels.cloud.order.request.OrderInfoSearchRequest;
import com.wheels.cloud.order.response.OrderInfoResponse;
import com.wheels.cloud.order.service.OrderInfoService;
import jakarta.inject.Inject;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * 订单详情 服务实现类
 */
@Service
public class OrderInfoServiceImpl extends ServiceImpl<OrderInfoMapper, OrderInfo> implements OrderInfoService, Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Inject
    private TokenService<SystemUserCacheVo, SysUser> tokenService;


    @Override
    public Boolean create(OrderInfoRequest request) {
        final var entity = new OrderInfo();
        BeanUtils.copyProperties(request, entity);
        return save(entity);
    }

    @Override
    public OrderInfoResponse getInfoByHash(String hash) {
        final var lqw = new LambdaQueryWrapper<OrderInfo>();
        lqw.eq(OrderInfo::getHash, hash);
        final var entity = getOne(lqw);
        if (ObjectUtil.isNull(entity)) {
            throw new CommonNullException("");
        }
        final var response = new OrderInfoResponse();
        BeanUtils.copyProperties(entity, response);
        return response;
    }

    @Override
    public Boolean softDeleteByHash(String hash) {
        final var lqw = new LambdaQueryWrapper<OrderInfo>();
        lqw.eq(OrderInfo::getHash, hash);
        final var entity = getOne(lqw);
        if (ObjectUtil.isNull(entity)) {
            throw new CommonNullException("");
        }
        return updateById(entity);
    }

    @Override
    public Boolean deleteByHash(String hash) {
        final var lqw = new LambdaQueryWrapper<OrderInfo>();
        lqw.eq(OrderInfo::getHash, hash);
        final var entity = getOne(lqw);
        if (ObjectUtil.isNull(entity)) {
            throw new CommonNullException("");
        }
        return removeById(entity);
    }

    @Override
    public Boolean updateEntity(OrderInfoRequest request) {
        final var hash = request.getHash();
        final var lqw = new LambdaQueryWrapper<OrderInfo>();
        lqw.eq(OrderInfo::getHash, hash);
        final var entity = getOne(lqw);
        if (ObjectUtil.isNull(entity)) {
            throw new CommonNullException("");
        }
        BeanUtils.copyProperties(request, entity);
        return updateById(entity);
    }

    @Override
    public List<OrderInfoResponse> getAdminList(OrderInfoSearchRequest request) {
        final var lqw = new LambdaQueryWrapper<OrderInfo>();
        final var entityList = list(lqw);
        final var responseList = new ArrayList<OrderInfoResponse>();
        entityList.forEach(entity -> {
            final var response = new OrderInfoResponse();
            BeanUtils.copyProperties(entity, response);
            responseList.add(response);
        });
        return responseList;
    }

    @Override
    public OrderInfoResponse getInfoByOrderHash(String orderHash) {
        final var lqw = new LambdaQueryWrapper<OrderInfo>();
        lqw.eq(OrderInfo::getOrderHash,orderHash);
        final var orderInfo = getOne(lqw);
        if (ObjectUtil.isNull(orderInfo)) {
            throw new CommonException("该条数据不存在！");
        }
        final var response = new OrderInfoResponse();
        BeanUtils.copyProperties(orderInfo,response);
        return response;
    }
}
