package com.sxhta.cloud.storage.service.impl;

import com.sxhta.cloud.storage.component.FileUploadComponent;
import com.sxhta.cloud.storage.service.ISysFileService;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.Serial;
import java.io.Serializable;

/**
 * 本地文件存储
 */
@Singleton
@Service
public class LocalSysFileServiceImpl implements ISysFileService, Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Inject
    private FileUploadComponent fileUploadComponent;

    /**
     * 资源映射路径 前缀
     */
    @Value("${file.prefix}")
    public String localFilePrefix;

    /**
     * 域名或本机访问地址
     */
    @Value("${file.domain}")
    public String domain;

    /**
     * 上传文件存储在本地的根路径
     */
    @Value("${file.path}")
    private String localFilePath;

    /**
     * 本地文件上传接口
     *
     * @param file 上传的文件
     * @return 访问地址
     */
    @Override
    public String uploadFile(MultipartFile file) throws Exception {
        final var name = fileUploadComponent.upload(localFilePath, file);
        return domain + localFilePrefix + name;
    }
}
