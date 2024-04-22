package com.sxhta.cloud.wheels.frontend.service.routeconfig;

import com.baomidou.mybatisplus.extension.service.IService;
import com.sxhta.cloud.wheels.frontend.entity.routeconfig.Area;

/**
 * 地区配置表 服务类
 */
public interface AreaService extends IService<Area> {

    String geAreaNameByHash(String departure);
}
