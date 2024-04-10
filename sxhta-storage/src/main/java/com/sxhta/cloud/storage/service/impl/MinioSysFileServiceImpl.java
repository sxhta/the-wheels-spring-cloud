package com.sxhta.cloud.storage.service.impl;

import com.alibaba.nacos.common.utils.IoUtils;
import com.sxhta.cloud.remote.vo.FileMetaVo;
import com.sxhta.cloud.storage.component.FileUploadComponent;
import com.sxhta.cloud.storage.config.MinioConfig;
import com.sxhta.cloud.storage.service.ISysFileService;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import jakarta.inject.Inject;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

/**
 * Minio 文件存储
 *
 * @author ruoyi
 */
@Service
public class MinioSysFileServiceImpl implements ISysFileService {

    @Inject
    private MinioConfig minioConfig;

    @Inject
    private MinioClient client;

    @Inject
    private FileUploadComponent fileUploadComponent;

    /**
     * Minio文件上传接口
     *
     * @param file 上传的文件
     * @return 访问地址
     */
    @Override
    public String uploadFile(MultipartFile file, String folder) throws Exception {
        final var fileName = fileUploadComponent.extractFilename(file);
        final var inputStream = file.getInputStream();
        final var bucketName = minioConfig.getBucketName();
        final var args = PutObjectArgs.builder()
                .bucket(bucketName)
                .object(fileName)
                .stream(inputStream, file.getSize(), -1)
                .contentType(file.getContentType())
                .build();
        client.putObject(args);
        IoUtils.closeQuietly(inputStream);
        return minioConfig.getUrl() + "/" + minioConfig.getBucketName() + "/" + fileName;
    }

    @Override
    public FileMetaVo getFileMeta() {
        return null;
    }
}
