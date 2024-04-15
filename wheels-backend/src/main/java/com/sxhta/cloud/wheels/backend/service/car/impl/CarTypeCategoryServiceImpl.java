package com.sxhta.cloud.wheels.backend.service.car.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sxhta.cloud.common.exception.ServiceException;
import com.sxhta.cloud.remote.domain.SysUser;
import com.sxhta.cloud.remote.vo.SystemUserCacheVo;
import com.sxhta.cloud.security.service.TokenService;
import com.sxhta.cloud.wheels.backend.entity.car.CarTypeCategory;
import com.sxhta.cloud.wheels.backend.mapper.car.CarTypeCategoryMapper;
import com.sxhta.cloud.wheels.backend.request.car.CarTypeCategoryRequest;
import com.sxhta.cloud.wheels.backend.request.car.CarTypeCategorySearchRequest;
import com.sxhta.cloud.wheels.backend.response.car.CarTypeCategoryResponse;
import com.sxhta.cloud.wheels.backend.service.car.CarTypeCategoryService;
import jakarta.inject.Inject;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class CarTypeCategoryServiceImpl extends ServiceImpl<CarTypeCategoryMapper, CarTypeCategory> implements CarTypeCategoryService {

    @Inject
    private TokenService<SystemUserCacheVo, SysUser> tokenService;


    @Override
    public Boolean create(CarTypeCategoryRequest carTypeCategoryRequest) {
        final var carTypeCategory = new CarTypeCategory();
        BeanUtils.copyProperties(carTypeCategoryRequest, carTypeCategory);
        return save(carTypeCategory);
    }

    @Override
    public CarTypeCategoryResponse getInfoByHash(String hash) {
        final var carTypeCategory = getEntity(hash);
        if (ObjectUtil.isNull(carTypeCategory)) {
            throw new ServiceException("该车辆类型异常，请联系管理员");
        }
        CarTypeCategoryResponse carTypeCategoryResponse = new CarTypeCategoryResponse();
        BeanUtils.copyProperties(carTypeCategory, carTypeCategoryResponse);
        return carTypeCategoryResponse;
    }

    @Override
    public Boolean softDeleteByHash(String hash) {
        final var carTypeCategory = getEntity(hash);
        if (ObjectUtil.isNull(carTypeCategory)) {
            throw new ServiceException("该车辆类型异常，请联系管理员");
        }
        carTypeCategory.setDeleteTime(LocalDateTime.now());
        return updateById(carTypeCategory);
    }

    @Override
    public Boolean deleteByHash(String hash) {
        return null;
    }

    @Override
    public Boolean updateEntity(CarTypeCategoryRequest carTypeCategoryRequest) {
        return null;
    }

    @Override
    public List<CarTypeCategoryResponse> getAdminList(CarTypeCategorySearchRequest request) {
        final var carTypeCategoryResponseList = new ArrayList<CarTypeCategoryResponse>();
        final var carTypeCategoryLqw = new LambdaQueryWrapper<CarTypeCategory>();
        carTypeCategoryLqw.isNull(CarTypeCategory::getDeleteTime);
        final var carTypeCategoryList = list(carTypeCategoryLqw);
        if (CollUtil.isNotEmpty(carTypeCategoryList)) {
            carTypeCategoryList.forEach(carTypeCategory -> {
                final var carTypeCategoryResponse = new CarTypeCategoryResponse();
                BeanUtils.copyProperties(carTypeCategory, carTypeCategoryResponse);
                carTypeCategoryResponseList.add(carTypeCategoryResponse);
            });
        }
        return carTypeCategoryResponseList;
    }

    private CarTypeCategory getEntity(String hash) {
        final var carTypeCategoryLqw = new LambdaQueryWrapper<CarTypeCategory>();
        carTypeCategoryLqw.eq(CarTypeCategory::getHash, hash)
                .isNull(CarTypeCategory::getDeleteTime);
        return getOne(carTypeCategoryLqw);
    }


}
