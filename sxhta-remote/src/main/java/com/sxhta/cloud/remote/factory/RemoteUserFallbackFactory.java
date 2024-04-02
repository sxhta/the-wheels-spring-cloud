package com.sxhta.cloud.remote.factory;

import com.sxhta.cloud.common.web.domain.CommonResponse;
import com.sxhta.cloud.remote.RemoteUserOpenFeign;
import com.sxhta.cloud.remote.domain.SysUser;
import com.sxhta.cloud.remote.model.SystemUserCacheVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;

/**
 * 用户服务降级处理
 */
@Component
public class RemoteUserFallbackFactory implements FallbackFactory<RemoteUserOpenFeign> {
    private final Logger logger = LoggerFactory.getLogger(RemoteUserFallbackFactory.class);

    @Override
    public RemoteUserOpenFeign create(Throwable throwable) {
        logger.error("用户服务调用失败:{}", throwable.getMessage());
        return new RemoteUserOpenFeign() {
            @Override
            public CommonResponse<SystemUserCacheVo> getUserInfo(String username, String source) {
                return CommonResponse.error("获取用户失败:" + throwable.getMessage());
            }

            @Override
            public CommonResponse<Boolean> registerUserInfo(SysUser sysUser, String source) {
                return CommonResponse.error("注册用户失败:" + throwable.getMessage());
            }
        };
    }
}
