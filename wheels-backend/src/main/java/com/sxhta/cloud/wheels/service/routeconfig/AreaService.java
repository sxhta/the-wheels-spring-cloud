package com.sxhta.cloud.wheels.service.routeconfig;

import com.sxhta.cloud.common.service.ICommonService;
import com.sxhta.cloud.wheels.request.AreaRequest;
import com.sxhta.cloud.wheels.request.AreaSearchRequest;
import com.sxhta.cloud.wheels.response.AreaResponse;

import java.util.List;

/**
 * 地区配置表 服务类
 */
public interface AreaService extends ICommonService<AreaSearchRequest, AreaRequest,AreaResponse> {

    List<String> getAreaNameListByAreaHash(String areaHash);
}
