package com.sxhta.cloud.wheels.service.impl;

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
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ComplainTypeServiceImpl extends ServiceImpl<ComplainTypeMapper, ComplainType> implements ComplainTypeService {

    @Inject
    private TokenService<SystemUserCacheVo, SysUser> tokenService;


    @Override
    public Boolean create(ComplainTypeRequest complainTypeRequest) {
        return null;
    }

    @Override
    public ComplainTypeResponse getInfoByHash(String hash) {
        return null;
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
        return null;
    }

    @Override
    public List<ComplainTypeResponse> getAdminList(ComplainTypeSearchRequest request) {
        return null;
    }
}




