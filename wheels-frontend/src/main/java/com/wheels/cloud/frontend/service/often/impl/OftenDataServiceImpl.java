package com.wheels.cloud.frontend.service.often.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sxhta.cloud.security.service.TokenService;
import com.sxhta.cloud.wheels.remote.domain.user.WheelsFrontUser;
import com.sxhta.cloud.wheels.remote.vo.FrontUserCacheVo;
import com.wheels.cloud.frontend.entity.often.OftenData;
import com.wheels.cloud.frontend.mapper.often.OftenDataMapper;
import com.wheels.cloud.frontend.request.often.OftenDataRequest;
import com.wheels.cloud.frontend.request.often.OftenDataSearchRequest;
import com.wheels.cloud.frontend.response.often.OftenDataResponse;
import com.wheels.cloud.frontend.service.often.OftenDataService;
import jakarta.inject.Inject;
import org.springframework.stereotype.Service;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

@Service
public class OftenDataServiceImpl extends ServiceImpl<OftenDataMapper, OftenData> implements OftenDataService, Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Inject
    private TokenService<FrontUserCacheVo, WheelsFrontUser> tokenService;


    @Override
    public Boolean create(OftenDataRequest oftenDataRequest) {
        return null;
    }

    @Override
    public OftenDataResponse getInfoByHash(String hash) {
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
    public Boolean updateEntity(OftenDataRequest oftenDataRequest) {
        return null;
    }

    @Override
    public List<OftenDataResponse> getAdminList(OftenDataSearchRequest request) {
        return null;
    }
}
