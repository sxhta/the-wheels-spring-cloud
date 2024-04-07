package com.sxhta.cloud.wheels.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.sxhta.cloud.common.service.ICommonService;
import com.sxhta.cloud.wheels.entity.RealNameOwner;
import com.sxhta.cloud.wheels.request.RealNameOwnerRequest;
import com.sxhta.cloud.wheels.request.RealNameOwnerSearchRequest;
import com.sxhta.cloud.wheels.response.RealNameOwnerResponse;

/**
 * 【wheels_real_name_owner(车主实名认证)】的数据库操作Service
 */
public interface RealNameOwnerService extends ICommonService<RealNameOwnerSearchRequest, RealNameOwnerRequest, RealNameOwnerResponse>, IService<RealNameOwner> {

}
