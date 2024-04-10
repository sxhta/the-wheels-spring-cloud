package com.sxhta.cloud.remote.factory;

import com.sxhta.cloud.common.web.domain.CommonResponse;
import com.sxhta.cloud.remote.RemoteFileOpenFeign;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;

/**
 * 文件服务降级处理
 */
@Component
public class RemoteFileFallbackFactory implements FallbackFactory<RemoteFileOpenFeign> {

    private final Logger logger = LoggerFactory.getLogger(RemoteFileFallbackFactory.class);

    @Override
    public RemoteFileOpenFeign create(Throwable cause) {
        logger.error("文件服务调用失败:{}", cause.getMessage());
        return (file, request) -> CommonResponse.error("上传文件失败:" + cause.getMessage());
    }
}
