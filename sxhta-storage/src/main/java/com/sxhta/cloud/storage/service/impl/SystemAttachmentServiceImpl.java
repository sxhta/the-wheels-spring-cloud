package com.sxhta.cloud.storage.service.impl;

import com.sxhta.cloud.storage.config.LocalFileConfig;
import com.sxhta.cloud.storage.service.SystemAttachmentService;
import jakarta.inject.Inject;
import org.springframework.stereotype.Service;

/**
 * SystemAttachmentServiceImpl 接口实现
 */
@Service
public class SystemAttachmentServiceImpl implements SystemAttachmentService {

    @Inject
    private LocalFileConfig localFileConfig;

    /**
     * 给图片加前缀
     *
     * @param fileUrl 文件路径
     * @return String
     */
    @Override
    public String prefixImage(String fileUrl) {
        return fileUrl = fileUrl.substring(fileUrl.indexOf("/") + 1);
    }

    /**
     * 给文件加前缀
     *
     * @param fileUrl 图片路径
     * @return String
     */
    @Override
    public String prefixFile(String fileUrl) {
        String path = localFileConfig.getPath();
        return path + "/" + fileUrl;
    }
}

