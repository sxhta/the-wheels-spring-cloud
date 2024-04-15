package com.sxhta.cloud.storage.config;

import com.sxhta.cloud.cache.redis.service.RedisService;
import com.sxhta.cloud.common.constant.CacheConstants;
import com.sxhta.cloud.remote.vo.FileMetaVo;
import com.sxhta.cloud.storage.service.ISysFileService;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.Serial;
import java.io.Serializable;

@Singleton
@Configuration
public class FileMetaConfiguration implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Inject
    private RedisService<String, FileMetaVo> redisService;

    @Inject
    private ISysFileService sysFileService;

    @Bean
    public FileMetaVo setMetaToRedis() {
        final var fileMetaVo = sysFileService.getFileMeta();
        redisService.setCacheObject(CacheConstants.LOCAL_FILE_META, fileMetaVo);
        return fileMetaVo;
    }
}
