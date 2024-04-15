package com.sxhta.cloud.content.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.sxhta.cloud.common.exception.CommonNullException;
import com.sxhta.cloud.content.response.UploadResponse;
import com.sxhta.cloud.content.service.ContentUploadService;
import com.sxhta.cloud.remote.RemoteFileOpenFeign;
import com.sxhta.cloud.remote.service.AttachmentService;
import jakarta.inject.Inject;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class ContentUploadServiceImpl implements ContentUploadService {

    @Inject
    private RemoteFileOpenFeign remoteFileOpenFeign;

    @Inject
    private AttachmentService attachmentService;

    @Inject
    private UploadResponse uploadResponse;

    @Override
    public UploadResponse uploadFile(MultipartFile file, String folder) {
       final var remoteResponse = remoteFileOpenFeign.upload(file, folder);
        if (ObjectUtil.isNull(remoteResponse)) {
            throw new CommonNullException("远程调用文件响应结果为空");
        }
        final var fileInfo = remoteResponse.getData();
        if (ObjectUtil.isNull(fileInfo)) {
            throw new CommonNullException("远程调用文件结果为空");
        }
        final var originUrl = fileInfo.getUrl();
        final var resultUrl = attachmentService.addPrefix(originUrl);
        final var fileName = fileInfo.getName();
        uploadResponse.setName(fileName)
                .setUrl(resultUrl);
        return uploadResponse;
    }
}
