package com.sxhta.cloud.wheels.auth.service.user;

import com.sxhta.cloud.common.domain.AbstractUserCacheVo;
import com.sxhta.cloud.common.domain.AbstractUserEntity;

public interface FrontUserLoginService<Cache extends AbstractUserCacheVo<Entity>, Entity extends AbstractUserEntity> {

    <T extends Cache> T login(String username, String password);

    void logout(String loginName);

    void register(String username, String password);
}