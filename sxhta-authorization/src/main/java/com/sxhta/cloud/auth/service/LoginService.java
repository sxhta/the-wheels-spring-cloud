package com.sxhta.cloud.auth.service;

import com.sxhta.cloud.remote.model.AbstractUserCacheVo;
import com.sxhta.cloud.remote.model.AbstractUserEntity;

public interface LoginService<Cache extends AbstractUserCacheVo<Entity>, Entity extends AbstractUserEntity> {

    <T extends Cache> T login(String username, String password);

    void logout(String loginName);

    void register(String username, String password);
}