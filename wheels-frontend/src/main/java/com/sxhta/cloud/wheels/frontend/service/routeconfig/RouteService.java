package com.sxhta.cloud.wheels.frontend.service.routeconfig;

import com.baomidou.mybatisplus.extension.service.IService;
import com.sxhta.cloud.wheels.frontend.entity.routeconfig.Route;
import com.sxhta.cloud.wheels.frontend.response.routeconfig.RouteResponse;

import java.util.List;

/**
 * 行程配置表 服务类
 */
public interface RouteService extends IService<Route> {

    List<RouteResponse> getDepartureList(String destinationHash);

    List<RouteResponse> getDestinationList(String departureHash);
}
