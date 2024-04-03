package com.sxhta.cloud.wheels.auth.service.owner;

import com.sxhta.cloud.common.domain.AbstractUserCacheVo;
import com.sxhta.cloud.common.domain.AbstractUserEntity;

public interface FrontOwnerLoginService<Cache extends AbstractUserCacheVo<Entity>, Entity extends AbstractUserEntity> {

    <T extends Cache> T login(String username, String password);

    void logout(String loginName);

    void register(String username, String password);
}