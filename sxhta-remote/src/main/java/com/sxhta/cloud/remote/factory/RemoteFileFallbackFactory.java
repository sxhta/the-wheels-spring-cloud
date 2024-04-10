package com.sxhta.cloud.remote.factory;

import com.sxhta.cloud.common.web.domain.CommonResponse;
import com.sxhta.cloud.remote.RemoteFileOpenFeign;
import com.sxhta.cloud.remote.domain.SysFile;
import com.sxhta.cloud.remote.vo.FileMetaVo;
import jakarta.inject.Singleton;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

/**
 * 文件服务降级处理
 */
@Singleton
@Component
public class RemoteFileFallbackFactory implements FallbackFactory<RemoteFileOpenFeign> {

    private final Logger logger = LoggerFactory.getLogger(RemoteFileFallbackFactory.class);

    @Override
    public RemoteFileOpenFeign create(Throwable cause) {

        return new RemoteFileOpenFeign() {
            @Override
            public CommonResponse<SysFile> upload(MultipartFile file, String path) {
                logger.error("文件服务调用失败:{}", cause.getMessage());
                return CommonResponse.error("上传文件失败:" + cause.getMessage());
            }

            @Override
            public CommonResponse<FileMetaVo> getFileMeta() {
                return CommonResponse.error("获取文件存储信息失败" + cause.getMessage());
            }
        };
    }
}