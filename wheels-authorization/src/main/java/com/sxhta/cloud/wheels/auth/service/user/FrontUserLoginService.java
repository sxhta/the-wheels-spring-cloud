package com.sxhta.cloud.wheels.auth.service.user;

import com.sxhta.cloud.common.domain.AbstractUserCacheVo;
import com.sxhta.cloud.common.domain.AbstractUserEntity;
import com.sxhta.cloud.wheels.auth.request.CheckCodeRequest;

public interface FrontUserLoginService<Cache extends AbstractUserCacheVo<Entity>, Entity extends AbstractUserEntity> {

    Cache loginOrRegisterByPhone(CheckCodeRequest request);
}