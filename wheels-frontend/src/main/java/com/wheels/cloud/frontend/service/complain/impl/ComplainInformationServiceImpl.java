package com.wheels.cloud.frontend.service.complain.impl;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sxhta.cloud.security.service.TokenService;
import com.sxhta.cloud.wheels.remote.domain.complain.ComplainInformation;
import com.sxhta.cloud.wheels.remote.domain.user.WheelsFrontUser;
import com.sxhta.cloud.wheels.remote.vo.FrontUserCacheVo;
import com.wheels.cloud.frontend.mapper.complain.ComplainInformationMapper;
import com.wheels.cloud.frontend.request.complain.ComplainInformationRequest;
import com.wheels.cloud.frontend.response.complain.ComplainInformationResponse;
import com.wheels.cloud.frontend.service.complain.ComplainInformationService;
import jakarta.inject.Inject;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static com.sxhta.cloud.common.utils.CharacterConvert.listToJsonString;

@Service
public class ComplainInformationServiceImpl extends ServiceImpl<ComplainInformationMapper, ComplainInformation> implements ComplainInformationService, Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Inject
    private TokenService<FrontUserCacheVo, WheelsFrontUser> tokenService;


    @Override
    public Boolean userComplainCreate(ComplainInformationRequest request) {
        final var complainInformation = new ComplainInformation();
        BeanUtils.copyProperties(request, complainInformation);
        complainInformation.setComplainUser(tokenService.getUsername())
                .setComplainTime(LocalDateTime.now())
                .setComplainPhotograph(listToJsonString(request.getComplainPhotograph()))
                .setCreateBy(tokenService.getUsername());
        return save(complainInformation);
    }

    @Override
    public List<ComplainInformationResponse> userComplainList() {
        final var complainInformationLqw = new LambdaQueryWrapper<ComplainInformation>();
        final var complainInformationResponseList = new ArrayList<ComplainInformationResponse>();
        complainInformationLqw.eq(ComplainInformation::getComplainUser, tokenService.getUsername())
                .isNull(ComplainInformation::getDeleteTime);
        final var complainInformationList = list(complainInformationLqw);
        if (CollUtil.isNotEmpty(complainInformationList)) {
            complainInformationList.forEach(complainInformation -> {
                final var complainInformationResponse = new ComplainInformationResponse();
                BeanUtils.copyProperties(complainInformation, complainInformationResponse);
                complainInformationResponseList.add(complainInformationResponse);
            });
        }
        return complainInformationResponseList;
    }

    @Override
    public void driverComplainList() {

    }
}
