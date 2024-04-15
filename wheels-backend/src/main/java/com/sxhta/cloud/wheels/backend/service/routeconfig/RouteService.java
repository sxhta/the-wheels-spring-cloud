package com.sxhta.cloud.wheels.backend.service.routeconfig;

import com.sxhta.cloud.common.service.ICommonService;
import com.sxhta.cloud.wheels.backend.request.RouteRequest;
import com.sxhta.cloud.wheels.backend.request.RouteSearchRequest;
import com.sxhta.cloud.wheels.backend.response.RouteResponse;
/**
 * 行程配置表 服务类
 */
public interface RouteService  extends ICommonService<RouteSearchRequest, RouteRequest,RouteResponse> {

}
