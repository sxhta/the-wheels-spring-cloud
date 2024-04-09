package com.sxhta.cloud.wheels.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sxhta.cloud.remote.domain.SysUser;
import com.sxhta.cloud.remote.vo.SystemUserCacheVo;
import com.sxhta.cloud.security.service.TokenService;
import com.sxhta.cloud.wheels.entity.complain.ComplainType;
import com.sxhta.cloud.wheels.mapper.ComplainTypeMapper;
import com.sxhta.cloud.wheels.request.complain.ComplainTypeRequest;
import com.sxhta.cloud.wheels.request.complain.ComplainTypeSearchRequest;
import com.sxhta.cloud.wheels.response.complain.ComplainTypeResponse;
import com.sxhta.cloud.wheels.service.ComplainTypeService;
import jakarta.inject.Inject;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ComplainTypeServiceImpl extends ServiceImpl<ComplainTypeMapper, ComplainType> implements ComplainTypeService {

    @Inject
    private TokenService<SystemUserCacheVo, SysUser> tokenService;


    @Override
    public Boolean create(ComplainTypeRequest complainTypeRequest) {
        final var complainType = new ComplainType();
        BeanUtils.copyProperties(complainTypeRequest, complainType);
        complainType.setCreateBy(tokenService.getUsername());
        return save(complainType);
    }

    @Override
    public ComplainTypeResponse getInfoByHash(String hash) {
        final var complainTypeLqw = new LambdaQueryWrapper<ComplainType>();
        complainTypeLqw.eq(ComplainType::getHash, hash);
        final var complainType = getOne(complainTypeLqw);
        final var complainTypeResponse = new ComplainTypeResponse();
        BeanUtils.copyProperties(complainType, complainTypeResponse);
        return complainTypeResponse;
    }

    @Override
    public Boolean softDeleteByHash(String hash) {
        return null;
    }

    @Override
    public Boolean deleteByHash(String hash) {
        return null;
    }

    @Override
    public Boolean updateEntity(ComplainTypeRequest complainTypeRequest) {
        ComplainType complainType = new ComplainType();
        BeanUtils.copyProperties(complainTypeRequest, complainType);
        complainType.setUpdateBy(tokenService.getUsername());
        return updateById(complainType);
    }

    @Override
    public List<ComplainTypeResponse> getAdminList(ComplainTypeSearchRequest request) {
        final var complainTypeResponseList = new ArrayList<ComplainTypeResponse>();
        final var complainTypeLqw = new LambdaQueryWrapper<ComplainType>();
        //TODO:投诉类型查询参数
        final var complainTypeList = list(complainTypeLqw);
        if (CollUtil.isNotEmpty(complainTypeList)) {
            complainTypeList.forEach(complainType -> {
                final var complainTypeResponse = new ComplainTypeResponse();
                BeanUtils.copyProperties(complainType, complainTypeResponse);
                complainTypeResponseList.add(complainTypeResponse);
            });
        }
        return complainTypeResponseList;
    }
}




