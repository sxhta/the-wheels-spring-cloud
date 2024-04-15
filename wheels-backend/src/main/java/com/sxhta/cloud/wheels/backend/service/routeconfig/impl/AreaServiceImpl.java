package com.sxhta.cloud.wheels.backend.service.routeconfig.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sxhta.cloud.common.domain.BaseHashEntity;
import com.sxhta.cloud.common.exception.CommonNullException;
import com.sxhta.cloud.remote.domain.SysUser;
import com.sxhta.cloud.remote.vo.SystemUserCacheVo;
import com.sxhta.cloud.security.service.TokenService;
import com.sxhta.cloud.wheels.backend.entity.routeconfig.Area;
import com.sxhta.cloud.wheels.backend.entity.routeconfig.Route;
import com.sxhta.cloud.wheels.backend.mapper.routeconfig.AreaMapper;
import com.sxhta.cloud.wheels.backend.mapper.routeconfig.RouteMapper;
import com.sxhta.cloud.wheels.backend.request.AreaRequest;
import com.sxhta.cloud.wheels.backend.request.AreaSearchRequest;
import com.sxhta.cloud.wheels.backend.response.AreaResponse;
import com.sxhta.cloud.wheels.backend.service.routeconfig.AreaService;
import jakarta.inject.Inject;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * 地区配置表 服务实现类
 */
@Service
public class AreaServiceImpl extends ServiceImpl<AreaMapper, Area> implements AreaService, Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Inject
    private TokenService<SystemUserCacheVo, SysUser> tokenService;

    @Inject
    private RouteMapper routeMapper;

    @Override
    public Boolean create(AreaRequest request) {
        final var res = this.getByAreaName(request.getAreaName());
        if (ObjectUtil.isNotNull(res)) {
            throw new CommonNullException("该地点已存在！");
        }
        final var entity = new Area();
        entity.setAreaName(request.getAreaName());
        final var loginUser = tokenService.getLoginUser();
        //TODO: 后管用户HASH
        entity.setCreateBy(String.valueOf(loginUser.getUserEntity().getUserId()));
        return save(entity);
    }

    @Override
    public Boolean deleteByHash(String hash) {
        final var res = this.getByHash(hash);
        if (ObjectUtil.isNull(res)) {
            throw new CommonNullException("该条数据不存在！");
        }
        if (this.getByDestination(res.getHash())) {
            throw new CommonNullException("该地点已使用！");
        }
        if (this.getByDepartureHash(res.getHash())) {
            throw new CommonNullException("该地点已使用！");
        }
        return removeById(res);
    }

    @Override
    public Boolean updateEntity(AreaRequest request) {
        final var res = this.getByHash(request.getHash());
        if (ObjectUtil.isNull(res)) {
            throw new CommonNullException("该条数据不存在！");
        }
        if (ObjectUtil.isNull(res)) {
            throw new CommonNullException("该条数据不存在！");
        }
        if (this.getByDestination(res.getHash())) {
            throw new CommonNullException("该地点已使用！");
        }
        if (this.getByDepartureHash(res.getHash())) {
            throw new CommonNullException("该地点已使用！");
        }
        final var lqw = new LambdaQueryWrapper<Area>();
        lqw.eq(Area::getAreaName,request.getAreaName())
           .ne(Area::getAreaName,res.getAreaName());
        final var area = getOne(lqw);
        if (ObjectUtil.isNotNull(area)) {
            throw new CommonNullException("该地点已存在！");
        }
        res.setAreaName(request.getAreaName());
        res.setUpdateTime(LocalDateTime.now());
        //TODO: 后管用户HASH
        final var loginUser = tokenService.getLoginUser();
        res.setUpdateBy(String.valueOf(loginUser.getUserEntity().getUserId()));
        return updateById(res);
    }

    @Override
    public AreaResponse getInfoByHash(String hash) {
        final var area = this.getByHash(hash);
        if (ObjectUtil.isNull(area)) {
            throw new CommonNullException("该条数据不存在！");
        }
        final var response = new AreaResponse();
        BeanUtils.copyProperties(area, response);
        return response;
    }

    @Override
    public List<AreaResponse> getAdminList(AreaSearchRequest request) {
        final var lqw = new LambdaQueryWrapper<Area>();
        lqw.isNull(Area::getDeleteTime);
        if (StrUtil.isNotBlank(request.getAreaName())) {
            lqw.and(i->i.like(Area::getAreaName,request.getAreaName()));
        }
        lqw.orderByDesc(Area::getCreateTime);
        final var entityList = list(lqw);
        final var responseList = new ArrayList<AreaResponse>();
        if (CollUtil.isEmpty(entityList)) {
            return responseList;
        }
        entityList.forEach(entity -> {
            final var response = new AreaResponse();
            BeanUtils.copyProperties(entity, response);
            responseList.add(response);
        });
        return responseList;
    }

    @Override
    public Boolean softDeleteByHash(String hash) {
        return null;
    }

    private Area getByAreaName(String areaName){
        final var lqw = new LambdaQueryWrapper<Area>();
        lqw.eq(Area::getAreaName,areaName);
        return getOne(lqw);
    }

    private Area getByHash(String hash){
        final var lqw = new LambdaQueryWrapper<Area>();
        lqw.eq(Area::getHash,hash);
        return getOne(lqw);
    }

    @Override
    public List<String> getAreaNameListByAreaHash(String areaHash) {
        final var lqw = new LambdaQueryWrapper<Area>();
        lqw.like(Area::getAreaName,areaHash);
        final var areaList = list(lqw);
        if (CollUtil.isEmpty(areaList)) {
            return null;
        }
        return areaList.stream().map(BaseHashEntity::getHash).toList();
    }

    public Boolean getByDepartureHash(String departureHash) {
        final var lqw = new LambdaQueryWrapper<Route>();
        lqw.eq(Route::getDeparture,departureHash);
        final var route = routeMapper.selectOne(lqw);
        if (ObjectUtil.isNull(route)) {
            return Boolean.FALSE;
        }
        return Boolean.TRUE;
    }


    public Boolean getByDestination(String destinationHash) {
        final var lqw = new LambdaQueryWrapper<Route>();
        lqw.eq(Route::getDestination,destinationHash);
        final var route = routeMapper.selectOne(lqw);
        if (ObjectUtil.isNull(route)) {
            return Boolean.FALSE;
        }
        return Boolean.TRUE;
    }
}