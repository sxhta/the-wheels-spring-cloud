package com.sxhta.cloud.wheels.remote.factory.owner;

import com.sxhta.cloud.common.web.domain.CommonResponse;
import com.sxhta.cloud.wheels.remote.openfeign.owner.FrontOwnerOpenFeign;
import com.sxhta.cloud.wheels.remote.vo.FrontUserCacheVo;
import com.sxhta.cloud.wheels.remote.domain.user.WheelsFrontUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;

/**
 * 用户服务降级处理
 */
@Component
public class FrontOwnerFallbackFactory implements FallbackFactory<FrontOwnerOpenFeign> {
    private final Logger logger = LoggerFactory.getLogger(FrontOwnerFallbackFactory.class);

    @Override
    public FrontOwnerOpenFeign create(Throwable throwable) {
        logger.error("用户服务调用失败:{}", throwable.getMessage());
        return new FrontOwnerOpenFeign() {
            @Override
            public CommonResponse<FrontUserCacheVo> getUserInfo(String username, String source) {
                return CommonResponse.error("获取用户失败:" + throwable.getMessage());
            }

            @Override
            public CommonResponse<Boolean> registerUserInfo(WheelsFrontUser sysUser, String source) {
                return CommonResponse.error("注册用户失败:" + throwable.getMessage());
            }
        };
    }
}
