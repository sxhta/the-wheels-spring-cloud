package com.sxhta.cloud.wheels.service.car.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sxhta.cloud.common.exception.ServiceException;
import com.sxhta.cloud.remote.domain.SysUser;
import com.sxhta.cloud.remote.vo.SystemUserCacheVo;
import com.sxhta.cloud.security.service.TokenService;
import com.sxhta.cloud.wheels.entity.car.CarType;
import com.sxhta.cloud.wheels.mapper.car.CarTypeMapper;
import com.sxhta.cloud.wheels.request.car.CarTypeRequest;
import com.sxhta.cloud.wheels.request.car.CarTypeSearchRequest;
import com.sxhta.cloud.wheels.response.car.CarTypeResponse;
import com.sxhta.cloud.wheels.service.car.CarTypeService;
import jakarta.inject.Inject;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class CarTypeServiceImpl extends ServiceImpl<CarTypeMapper, CarType> implements CarTypeService {

    //TODO:车辆类型 后管人员
    @Inject
    private TokenService<SystemUserCacheVo, SysUser> tokenService;

    @Override
    public Boolean create(CarTypeRequest carTypeRequest) {
        final var carType = new CarType();
        BeanUtils.copyProperties(carTypeRequest, carType);
        carType.setCreateBy(tokenService.getLoginUser().getUsername());
        return save(carType);
    }

    @Override
    public CarTypeResponse getInfoByHash(String hash) {
        final var carType = getEntity(hash);
        final var carTypeResponse = new CarTypeResponse();
        BeanUtils.copyProperties(carType, carTypeResponse);
        return carTypeResponse;
    }

    @Override
    public Boolean softDeleteByHash(String hash) {
        final var carType = getEntity(hash);
        carType.setDeleteTime(LocalDateTime.now());
        return updateById(carType);
    }

    @Override
    public Boolean deleteByHash(String hash) {
        final var carType = getEntity(hash);
        return removeById(carType);
    }

    @Override
    public Boolean updateEntity(CarTypeRequest carTypeRequest) {
        final var carType = new CarType();
        BeanUtils.copyProperties(carTypeRequest, carType);
        carType.setUpdateBy(tokenService.getUsername())
                .setUpdateTime(LocalDateTime.now());
        return updateById(carType);
    }

    @Override
    public List<CarTypeResponse> getAdminList(CarTypeSearchRequest request) {
        final var carTypeResponseList = new ArrayList<CarTypeResponse>();
        final var carTypeLqw = new LambdaQueryWrapper<CarType>();
        final var status = request.getStatus();
        final var createBy = request.getCreateBy();
        final var updateBy = request.getUpdateBy();
        if (ObjectUtil.isNotNull(status)) {
            carTypeLqw.eq(CarType::getStatus, status);
        }
        if (StrUtil.isNotBlank(createBy)) {
            carTypeLqw.like(CarType::getCreateBy, createBy);
        }
        if (StrUtil.isNotBlank(updateBy)) {
            carTypeLqw.like(CarType::getUpdateBy, updateBy);
        }
        carTypeLqw.isNull(CarType::getDeleteTime);
        final var carTypeList = list(carTypeLqw);
        if (CollUtil.isNotEmpty(carTypeList)) {
            carTypeList.forEach(carType -> {
                final var carTypeResponse = new CarTypeResponse();
                BeanUtils.copyProperties(carType, carTypeResponse);
                carTypeResponseList.add(carTypeResponse);
            });
        }
        return carTypeResponseList;
    }

    private CarType getEntity(String hash) {
        final var carTypeLqw = new LambdaQueryWrapper<CarType>();
        carTypeLqw.eq(CarType::getHash, hash);
        final var carType = getOne(carTypeLqw);
        if (ObjectUtil.isNull(carType)) {
            throw new ServiceException("该车辆类型异常，请联系管理员");
        }
        return carType;
    }

    @Override
    public List<CarType> getCarTypeAll() {
        final var carTypeLqw = new LambdaQueryWrapper<CarType>();
        carTypeLqw.eq(CarType::getStatus, 0)
                .isNull(CarType::getDeleteTime);
        return list(carTypeLqw);
    }
}
