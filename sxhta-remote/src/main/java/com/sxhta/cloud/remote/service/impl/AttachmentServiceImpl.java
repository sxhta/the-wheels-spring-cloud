package com.sxhta.cloud.remote.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.sxhta.cloud.cache.redis.service.RedisService;
import com.sxhta.cloud.common.constant.CacheConstants;
import com.sxhta.cloud.remote.service.AttachmentService;
import com.sxhta.cloud.remote.vo.FileMetaVo;
import jakarta.annotation.PostConstruct;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import org.springframework.stereotype.Service;

import java.io.Serial;
import java.io.Serializable;

/**
 * SystemAttachmentServiceImpl 接口实现
 */
@Singleton
@Service
public class AttachmentServiceImpl implements AttachmentService, Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private FileMetaVo fileMetaVo;

    @Inject
    private RedisService<String, FileMetaVo> redisService;

    @PostConstruct
    private void init() {
        this.fileMetaVo = redisService.getCacheObject(CacheConstants.LOCAL_FILE_META);
    }

    @Override
    public FileMetaVo getFileMetaVo() {
        return this.fileMetaVo;
    }

    @Override
    public String getDomain() {
        return this.fileMetaVo.getDomain();
    }

    /**
     * 给图片加前缀
     *
     * @param url 文件路径
     * @return String
     */
    @Override
    public String clearPrefix(String url) {
        final var domain = fileMetaVo.getDomain();
        return url.replace(domain, "");
    }


    /**
     * 给文件加前缀
     *
     * @param domain 域名
     * @param prefix 前缀
     * @return String
     */
    @Override
    public String addPrefix(String target, String domain, String prefix) {
        if (ObjectUtil.isNull(target)) {
            return target;
        }
        return target.replace(prefix, domain + prefix);
    }

    @Override
    public String addPrefix(String target) {
        final var domain = fileMetaVo.getDomain();
        final var prefix = fileMetaVo.getPrefix();
        return addPrefix(target, domain, prefix);
    }
}