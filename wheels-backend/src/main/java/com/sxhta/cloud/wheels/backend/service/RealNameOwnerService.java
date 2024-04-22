package com.sxhta.cloud.wheels.backend.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.sxhta.cloud.common.service.ICommonService;
import com.sxhta.cloud.wheels.backend.entity.RealNameOwner;
import com.sxhta.cloud.wheels.backend.request.RealNameOwnerRequest;
import com.sxhta.cloud.wheels.backend.request.RealNameOwnerSearchRequest;
import com.sxhta.cloud.wheels.backend.response.RealNameOwnerResponse;

/**
 * 【wheels_real_name_owner(车主实名认证)】的数据库操作Service
 */
public interface RealNameOwnerService extends ICommonService<RealNameOwnerSearchRequest, RealNameOwnerRequest, RealNameOwnerResponse>, IService<RealNameOwner> {

}
