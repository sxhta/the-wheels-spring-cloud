package com.sxhta.cloud.storage.service.impl;

import cn.hutool.core.util.StrUtil;
import com.sxhta.cloud.remote.vo.FileMetaVo;
import com.sxhta.cloud.storage.component.FileUploadComponent;
import com.sxhta.cloud.storage.config.LocalFileConfig;
import com.sxhta.cloud.storage.service.ISysFileService;
import jakarta.annotation.Nullable;
import jakarta.inject.Inject;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * 本地文件存储
 */
@Primary
@Service
public class LocalSysFileServiceImpl implements ISysFileService {

    @Inject
    private FileUploadComponent fileUploadComponent;

    @Inject
    private LocalFileConfig localFileConfig;

    /**
     * 本地文件上传接口
     *
     * @param file 上传的文件
     * @return 访问地址
     */
    @Override
    public String uploadFile(@RequestPart(value = "file") MultipartFile file, @Nullable String folder)
            throws IOException {
        var localFilePath = localFileConfig.getPath();
        if (StrUtil.isNotBlank(folder)) {
            if (folder.charAt(0) != '/') {
                folder = "/" + folder;
            }
            localFilePath = localFilePath + folder;
        }
        //去掉域名
        //final var domain = localFileConfig.getDomain();
        final var localFilePrefix = localFileConfig.getPrefix();
        final var name = fileUploadComponent.upload(localFilePath, file);
        return localFilePrefix + folder + name;
    }

    @Override
    public FileMetaVo getFileMeta() {
        final var fileMetaVo = new FileMetaVo();
        final var domain = localFileConfig.getDomain();
        final var prefix = localFileConfig.getPrefix();
        final var path = localFileConfig.getPath();
        fileMetaVo.setDomain(domain)
                .setPrefix(prefix)
                .setPath(path);
        return fileMetaVo;
    }
}
