package com.sxhta.cloud.wheels.backend.service.routeconfig;

import com.sxhta.cloud.common.service.ICommonService;
import com.sxhta.cloud.wheels.backend.request.AreaRequest;
import com.sxhta.cloud.wheels.backend.request.AreaSearchRequest;
import com.sxhta.cloud.wheels.backend.response.AreaResponse;

import java.util.List;

/**
 * 地区配置表 服务类
 */
public interface AreaService extends ICommonService<AreaSearchRequest, AreaRequest,AreaResponse> {

    List<String> getAreaNameListByAreaHash(String areaHash);
}
