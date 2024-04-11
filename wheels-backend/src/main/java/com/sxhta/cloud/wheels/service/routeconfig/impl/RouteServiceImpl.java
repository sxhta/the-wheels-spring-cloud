package com.sxhta.cloud.wheels.service.routeconfig.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sxhta.cloud.common.exception.CommonNullException;
import com.sxhta.cloud.remote.domain.SysUser;
import com.sxhta.cloud.remote.vo.SystemUserCacheVo;
import com.sxhta.cloud.security.service.TokenService;
import com.sxhta.cloud.wheels.entity.routeconfig.Route;
import com.sxhta.cloud.wheels.mapper.routeconfig.RouteMapper;
import com.sxhta.cloud.wheels.request.RouteRequest;
import com.sxhta.cloud.wheels.request.RouteSearchRequest;
import com.sxhta.cloud.wheels.response.RouteResponse;
import com.sxhta.cloud.wheels.service.routeconfig.AreaService;
import com.sxhta.cloud.wheels.service.routeconfig.RouteService;
import jakarta.inject.Inject;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * 行程配置表 服务实现类
 */
@Service
public class RouteServiceImpl extends ServiceImpl<RouteMapper, Route> implements RouteService, Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Inject
    private TokenService<SystemUserCacheVo, SysUser> tokenService;

    @Inject
    private AreaService areaService;


    @Override
    public Boolean create(RouteRequest request) {
        this.isSame(request.getDeparture(), request.getDestination());
        this.isHave(request.getDeparture(), request.getDestination());
        final var route = new Route();
        BeanUtils.copyProperties(request, route);

        //TODO: 后管用户HASH
        final var loginUser = tokenService.getLoginUser();
        route.setCreateBy(String.valueOf(loginUser.getUserEntity().getUserId()));
        return save(route);
    }

    @Override
    public Boolean deleteByHash(String hash) {
        final var route = this.getByHash(hash);
        if (ObjectUtil.isNull(route)) {
            throw new CommonNullException("该数据不存在！");
        }
        return removeById(route);
    }

    @Override
    public Boolean updateEntity(RouteRequest request) {
        final var route = this.getByHash(request.getHash());
        if (ObjectUtil.isNull(route)) {
            throw new CommonNullException("该数据不存在！");
        }
        this.isSame(request.getDeparture(), request.getDestination());
        //起点或终点修改时才进入判断路线
        if (!route.getDestination().equals(request.getDestination()) || !route.getDeparture().equals(request.getDeparture())) {
            this.isHave(request.getDeparture(),request.getDestination());
        }
        route.setDeparture(request.getDeparture());
        route.setDestination(request.getDestination());
        route.setMileage(request.getMileage());
        route.setPrice(request.getPrice());
        route.setMark(request.getMark());
        //TODO: 后管用户HASH
        final var loginUser = tokenService.getLoginUser();
        route.setUpdateBy(String.valueOf(loginUser.getUserEntity().getUserId()));
        route.setUpdateTime(LocalDateTime.now());
        return updateById(route);
    }

    @Override
    public RouteResponse getInfoByHash(String hash) {
        final var route = this.getByHash(hash);
        if (ObjectUtil.isNull(route)) {
            throw new CommonNullException("该数据不存在！");
        }
        final var response = new RouteResponse();
        BeanUtils.copyProperties(route, response);
        response.setDeparture(areaService.getInfoByHash(route.getDeparture()).getAreaName());
        response.setDestination(areaService.getInfoByHash(route.getDestination()).getAreaName());
        return response;
    }

    @Override
    public List<RouteResponse> getAdminList(RouteSearchRequest request) {
        final var lqw = new LambdaQueryWrapper<Route>();
        lqw.isNull(Route::getDeleteTime);
        if (ObjectUtil.isNotNull(request.getDeparture())) {
            final var departureList = areaService.getAreaNameListByAreaHash(request.getDeparture());
            if (CollUtil.isNotEmpty(departureList)) {
               lqw.and(i->i.in(Route::getDeparture,departureList)) ;
            }
        }
        if (ObjectUtil.isNotNull(request.getDestination())) {
            final var destinationList = areaService.getAreaNameListByAreaHash(request.getDestination());
            if (CollUtil.isNotEmpty(destinationList)) {
                lqw.and(i->i.in(Route::getDestination,destinationList)) ;
            }
        }
        lqw.orderByDesc(Route::getCreateTime);
        final var entityList = list(lqw);
        final var responseList = new ArrayList<RouteResponse>();
        if (CollUtil.isEmpty(entityList)) {
            return responseList;
        }
        entityList.forEach(entity -> {
            final var response = new RouteResponse();
            BeanUtils.copyProperties(entity, response);
            response.setDeparture(areaService.getInfoByHash(entity.getDeparture()).getAreaName());
            response.setDestination(areaService.getInfoByHash(entity.getDestination()).getAreaName());
            responseList.add(response);
        });
        return responseList;
    }

    private Route getByHash(String hash){
        final var lqw = new LambdaQueryWrapper<Route>();
        lqw.eq(Route::getHash,hash);
        return getOne(lqw);
    }

    /**
     * 判断起点和终点是否一致
     */
    private void isSame(String departure, String destination) {
        if (departure.equals(destination)) {
            throw new CommonNullException("起点和终点不能一致！");
        }
    }

    /**
     * 通过起点判断是否有重复的路线
     */
    private void isHave(String departure, String destination) {
        areaService.getInfoByHash(departure);
        areaService.getInfoByHash(destination);
        final var lqw = new LambdaQueryWrapper<Route>();
        lqw.and(i -> i.eq(Route::getDeparture, departure));
        final var routeList = list(lqw);
        final var destinationLqw = new LambdaQueryWrapper<Route>();
        destinationLqw.and(i -> i.eq(Route::getDeparture, departure));
        if (CollUtil.isNotEmpty(routeList)) {
            routeList.forEach(route -> {
                if (route.getDestination().equals(destination)) {
                    throw new CommonNullException("该条行程配置已存在！");
                }
            });
        }

    }

    @Override
    public Boolean softDeleteByHash(String hash) {
        return null;
    }


}
