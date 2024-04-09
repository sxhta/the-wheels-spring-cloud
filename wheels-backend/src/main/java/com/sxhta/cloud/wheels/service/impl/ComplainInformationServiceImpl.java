package com.sxhta.cloud.wheels.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sxhta.cloud.remote.domain.SysUser;
import com.sxhta.cloud.remote.vo.SystemUserCacheVo;
import com.sxhta.cloud.security.service.TokenService;
import com.sxhta.cloud.wheels.entity.complain.ComplainInformation;
import com.sxhta.cloud.wheels.mapper.ComplainInformationMapper;
import com.sxhta.cloud.wheels.request.complain.ComplainInformationRequest;
import com.sxhta.cloud.wheels.request.complain.ComplainInformationSearchRequest;
import com.sxhta.cloud.wheels.response.complain.ComplainInformationResponse;
import com.sxhta.cloud.wheels.service.ComplainInformationService;
import jakarta.inject.Inject;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ComplainInformationServiceImpl extends ServiceImpl<ComplainInformationMapper, ComplainInformation> implements ComplainInformationService {

    @Inject
    private TokenService<SystemUserCacheVo, SysUser> tokenService;


    @Override
    public Boolean create(ComplainInformationRequest complainInformationRequest) {
        return null;
    }

    @Override
    public ComplainInformationResponse getInfoByHash(String hash) {
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
    public Boolean updateEntity(ComplainInformationRequest complainInformationRequest) {
        return null;
    }

    @Override
    public List<ComplainInformationResponse> getAdminList(ComplainInformationSearchRequest request) {
        return null;
    }
}




