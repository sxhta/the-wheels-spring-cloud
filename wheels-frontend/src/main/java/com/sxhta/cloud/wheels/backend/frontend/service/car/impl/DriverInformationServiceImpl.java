package com.sxhta.cloud.wheels.frontend.service.car.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sxhta.cloud.wheels.frontend.entity.car.DriverInformation;
import com.sxhta.cloud.wheels.frontend.mapper.car.DriverInformationMapper;
import com.sxhta.cloud.wheels.frontend.request.car.DriverInformationRequest;
import com.sxhta.cloud.wheels.frontend.request.car.DriverInformationSearchRequest;
import com.sxhta.cloud.wheels.frontend.response.car.DriverInformationResponse;
import com.sxhta.cloud.wheels.frontend.service.car.DriverInformationService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class DriverInformationServiceImpl extends ServiceImpl<DriverInformationMapper, DriverInformation> implements DriverInformationService, Serializable {
    @Override
    public Boolean create(DriverInformationRequest driverInformationRequest) {
        final var driverInformation = new DriverInformation();
        BeanUtils.copyProperties(driverInformationRequest, driverInformation);
        //TODO:上传的多文件需要转换
        return save(driverInformation);
    }

    @Override
    public DriverInformationResponse getInfoByHash(String hash) {
        final var driverInformationLqw = new LambdaQueryWrapper<DriverInformation>();
        driverInformationLqw.eq(DriverInformation::getHash, hash);
        final var driverInformation = getOne(driverInformationLqw);
        //TODO:1.判断是否查询到详情，2.将多图片地址转换数组传回去
        final var driverInformationResponse = new DriverInformationResponse();
        BeanUtils.copyProperties(driverInformation, driverInformationResponse);
        return driverInformationResponse;
    }

    @Override
    public Boolean softDeleteByHash(String hash) {
        final var driverInformationLqw = new LambdaQueryWrapper<DriverInformation>();
        driverInformationLqw.eq(DriverInformation::getHash, hash);
        final var driverInformation = getOne(driverInformationLqw);
        driverInformation.setDeleteTime(LocalDateTime.now());
        return updateById(driverInformation);
    }

    @Override
    public Boolean deleteByHash(String hash) {
        final var driverInformationLqw = new LambdaQueryWrapper<DriverInformation>();
        driverInformationLqw.eq(DriverInformation::getHash, hash);
        final var driverInformation = getOne(driverInformationLqw);
        //TODO:判断是否存在
        return removeById(driverInformation);
    }

    @Override
    public Boolean updateEntity(DriverInformationRequest driverInformationRequest) {
        final var driverInformation = new DriverInformation();
        BeanUtils.copyProperties(driverInformationRequest, driverInformation);
        //TODO:上传的多文件需要转换
        return updateById(driverInformation);
    }

    @Override
    public List<DriverInformationResponse> getAdminList(DriverInformationSearchRequest request) {
        final var driverInformationResponseList = new ArrayList<DriverInformationResponse>();
        final var driverInformationLqw = new LambdaQueryWrapper<DriverInformation>();
        //TODO:模糊查询参数
//        driverInformationLqw.eq(DriverInformation::)
        final var driverInformationList = list(driverInformationLqw);
        driverInformationList.forEach(driverInformation -> {
            final var driverInformationResponse = new DriverInformationResponse();
            BeanUtils.copyProperties(driverInformation, driverInformationResponse);
            //TODO:图片转换数组
        });
        return driverInformationResponseList;
    }
}
