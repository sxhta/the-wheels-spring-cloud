package com.sxhta.cloud.wheels.auth.service.user.impl;

import com.sxhta.cloud.wheels.auth.service.user.FrontUserPasswordService;
import com.sxhta.cloud.wheels.auth.service.user.FrontUserRecordLogService;
import com.sxhta.cloud.cache.redis.service.RedisService;
import com.sxhta.cloud.common.constant.CacheConstants;
import com.sxhta.cloud.common.constant.Constants;
import com.sxhta.cloud.common.exception.ServiceException;
import com.sxhta.cloud.common.domain.AbstractUserEntity;
import com.sxhta.cloud.security.service.SecurityService;
import jakarta.inject.Inject;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

/**
 * 登录密码方法
 */
@Service
public class FrontUserPasswordServiceImpl implements FrontUserPasswordService {

    @Inject
    private RedisService<String, Integer> redisService;

    private static final Integer maxRetryCount = CacheConstants.PASSWORD_MAX_RETRY_COUNT;

    private static final Long lockTime = CacheConstants.PASSWORD_LOCK_TIME;

    @Inject
    private FrontUserRecordLogService recordLogService;

    @Inject
    private SecurityService securityService;

    /**
     * 登录账户密码错误次数缓存键名
     *
     * @param username 用户名
     * @return 缓存键key
     */
    @Override
    public String getCacheKey(String username) {
        return CacheConstants.PWD_ERR_CNT_KEY + username;
    }

    @Override
    public void validate(AbstractUserEntity user, String password) {
        final var username = user.getUserName();

        var retryCount = redisService.getCacheObject(getCacheKey(username));

        if (retryCount == null) {
            retryCount = 0;
        }

        if (retryCount >= maxRetryCount) {
            final var errMsg = String.format("密码输入错误%s次，帐户锁定%s分钟", maxRetryCount, lockTime);
            recordLogService.recordLoginInfo(username, Constants.LOGIN_FAIL, errMsg);
            throw new ServiceException(errMsg);
        }

        if (!matches(user, password)) {
            retryCount = retryCount + 1;
            recordLogService.recordLoginInfo(username, Constants.LOGIN_FAIL, String.format("密码输入错误%s次", retryCount));
            redisService.setCacheObject(getCacheKey(username), retryCount, lockTime, TimeUnit.MINUTES);
            throw new ServiceException("用户不存在/密码错误");
        } else {
            clearLoginRecordCache(username);
        }
    }

    @Override
    public Boolean matches(AbstractUserEntity user, String rawPassword) {
        return securityService.matchesPassword(rawPassword, user.getPassword());
    }

    @Override
    public void clearLoginRecordCache(String loginName) {
        if (redisService.hasKey(getCacheKey(loginName))) {
            redisService.deleteObject(getCacheKey(loginName));
        }
    }
}
