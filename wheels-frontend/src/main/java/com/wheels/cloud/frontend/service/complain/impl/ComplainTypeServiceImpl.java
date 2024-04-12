package com.wheels.cloud.frontend.service.complain.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sxhta.cloud.security.service.TokenService;
import com.sxhta.cloud.wheels.remote.domain.complain.ComplainType;
import com.sxhta.cloud.wheels.remote.domain.user.WheelsFrontUser;
import com.sxhta.cloud.wheels.remote.vo.FrontUserCacheVo;
import com.wheels.cloud.frontend.mapper.complain.ComplainTypeMapper;
import com.wheels.cloud.frontend.service.complain.ComplainTypeService;
import jakarta.inject.Inject;
import org.springframework.stereotype.Service;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

@Service
public class ComplainTypeServiceImpl extends ServiceImpl<ComplainTypeMapper, ComplainType> implements ComplainTypeService, Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Inject
    private TokenService<FrontUserCacheVo, WheelsFrontUser> tokenService;

    @Override
    public List<ComplainType> getComplainTypeList() {
        final var complainTypeLqw = new LambdaQueryWrapper<ComplainType>();
        complainTypeLqw.eq(ComplainType::getStatus, true)
                .isNull(ComplainType::getDeleteTime);
        return list(complainTypeLqw);
    }
}
