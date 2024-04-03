package com.sxhta.cloud.wheels.auth.service.owner;

import com.sxhta.cloud.common.domain.AbstractUserEntity;

/**
 * 登录密码方法
 */
public interface FrontOwnerPasswordService {

    String getCacheKey(String username);

    void validate(AbstractUserEntity user, String password);

    Boolean matches(AbstractUserEntity user, String rawPassword);

    void clearLoginRecordCache(String loginName);
}
