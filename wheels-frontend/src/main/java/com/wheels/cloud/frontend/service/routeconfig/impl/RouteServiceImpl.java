package com.wheels.cloud.frontend.service.routeconfig.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wheels.cloud.frontend.entity.routeconfig.Route;
import com.wheels.cloud.frontend.mapper.routeconfig.RouteMapper;
import com.wheels.cloud.frontend.response.routeconfig.RouteResponse;
import com.wheels.cloud.frontend.service.routeconfig.AreaService;
import com.wheels.cloud.frontend.service.routeconfig.RouteService;
import jakarta.inject.Inject;
import org.springframework.stereotype.Service;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 行程配置表 服务实现类
 */
@Service
public class RouteServiceImpl extends ServiceImpl<RouteMapper, Route> implements RouteService, Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Inject
    private AreaService areaService;

    @Override
    public List<RouteResponse> getDepartureList(String destinationHash) {
        final var lqw = new LambdaQueryWrapper<Route>();
        lqw.and(i->i.isNull(Route::getDeleteTime));
        if (StrUtil.isNotBlank(destinationHash)) {
            lqw.and(i->i.eq(Route::getDestination,destinationHash));
        }
        final var routeList = list(lqw);
        final var responseList = new ArrayList<RouteResponse>();
        if (CollUtil.isEmpty(routeList)) {
            return responseList;
        }
        return routeList.stream().map(route->{
            final var response = new RouteResponse();
            response.setAreaHash(route.getDeparture());
            response.setAreaName(areaService.geAreaNameByHash(route.getDeparture()));
            return response;
        }).collect(Collectors.toList());
    }

    @Override
    public List<RouteResponse> getDestinationList(String departureHash) {
        final var lqw = new LambdaQueryWrapper<Route>();
        lqw.and(i->i.isNull(Route::getDeleteTime));
        if (StrUtil.isNotBlank(departureHash)) {
            lqw.and(i->i.eq(Route::getDeparture,departureHash));
        }
        final var routeList = list(lqw);
        final var responseList = new ArrayList<RouteResponse>();
        if (CollUtil.isEmpty(routeList)) {
            return responseList;
        }
        return routeList.stream().map(route->{
            final var response = new RouteResponse();
            response.setAreaHash(route.getDestination());
            response.setAreaName(areaService.geAreaNameByHash(route.getDestination()));
            return response;
        }).collect(Collectors.toList());
    }
}
