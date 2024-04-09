package com.sxhta.cloud.wheels.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sxhta.cloud.remote.domain.SysUser;
import com.sxhta.cloud.remote.vo.SystemUserCacheVo;
import com.sxhta.cloud.security.service.TokenService;
import com.sxhta.cloud.wheels.entity.feedback.FeedbackType;
import com.sxhta.cloud.wheels.mapper.FeedbackTypeMapper;
import com.sxhta.cloud.wheels.request.feedback.FeedbackTypeRequest;
import com.sxhta.cloud.wheels.request.feedback.FeedbackTypeSearchRequest;
import com.sxhta.cloud.wheels.response.feedback.FeedbackTypeResponse;
import com.sxhta.cloud.wheels.service.FeedbackTypeService;
import jakarta.inject.Inject;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FeedbackTypeServiceImpl extends ServiceImpl<FeedbackTypeMapper, FeedbackType> implements FeedbackTypeService {

    @Inject
    private TokenService<SystemUserCacheVo, SysUser> tokenService;


    @Override
    public Boolean create(FeedbackTypeRequest feedbackTypeRequest) {
        return null;
    }

    @Override
    public FeedbackTypeResponse getInfoByHash(String hash) {
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
    public Boolean updateEntity(FeedbackTypeRequest feedbackTypeRequest) {
        return null;
    }

    @Override
    public List<FeedbackTypeResponse> getAdminList(FeedbackTypeSearchRequest request) {
        return null;
    }
}




