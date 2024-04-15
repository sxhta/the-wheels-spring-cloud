package com.sxhta.cloud.wheels.remote.factory.user;

import com.sxhta.cloud.common.web.domain.CommonResponse;
import com.sxhta.cloud.wheels.remote.openfeign.user.FrontUserOpenFeign;
import com.sxhta.cloud.wheels.remote.request.RemoteRegisterRequest;
import com.sxhta.cloud.wheels.remote.vo.FrontUserCacheVo;
import com.sxhta.cloud.wheels.remote.vo.FrontUserHashVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

/**
 * 用户服务降级处理
 */
@Component
public class FrontUserFallbackFactory implements FallbackFactory<FrontUserOpenFeign>, Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private final Logger logger = LoggerFactory.getLogger(FrontUserFallbackFactory.class);

    @Override
    public FrontUserOpenFeign create(Throwable throwable) {
        logger.error("用户服务调用失败:{}", throwable.getMessage());
        return new FrontUserOpenFeign() {
            @Override
            public CommonResponse<FrontUserCacheVo> getUserInfo(String username, String source) {
                return CommonResponse.error("获取用户失败:" + throwable.getMessage());
            }

            @Override
            public CommonResponse<Boolean> register(RemoteRegisterRequest sysUser, String source) {
                return CommonResponse.error("注册用户失败:" + throwable.getMessage());
            }
        };
    }
}
