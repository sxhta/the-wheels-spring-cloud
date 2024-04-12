package com.wheels.cloud.frontend.service.routeconfig.impl;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sxhta.cloud.common.exception.CommonNullException;
import com.wheels.cloud.frontend.entity.routeconfig.Area;
import com.wheels.cloud.frontend.mapper.routeconfig.AreaMapper;
import com.wheels.cloud.frontend.service.routeconfig.AreaService;
import org.springframework.stereotype.Service;

import java.io.Serial;
import java.io.Serializable;

/**
 * 地区配置表 服务实现类
 */
@Service
public class AreaServiceImpl extends ServiceImpl<AreaMapper, Area> implements AreaService, Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Override
    public String geAreaNameByHash(String hash) {
        final var lqw = new LambdaQueryWrapper<Area>();
        lqw.eq(Area::getHash,hash);
        final var area = getOne(lqw);
        if (ObjectUtil.isNull(area)) {
            throw new CommonNullException("该条数据不存在!");
        }
        return area.getAreaName();
    }
}
