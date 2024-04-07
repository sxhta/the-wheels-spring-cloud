package com.sxhta.cloud.storage.controller;

import com.sxhta.cloud.common.component.FileComponent;
import com.sxhta.cloud.common.web.domain.CommonResponse;
import com.sxhta.cloud.remote.domain.SysFile;
import com.sxhta.cloud.storage.service.ISysFileService;
import jakarta.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * 文件请求处理
 *
 * @author ruoyi
 */
@RestController
public class SysFileController {
    private static final Logger log = LoggerFactory.getLogger(SysFileController.class);

    @Inject
    private ISysFileService sysFileService;

    @Inject
    private FileComponent fileComponent;

    /**
     * 文件上传请求
     */
    @PostMapping("upload")
    public CommonResponse<SysFile> upload(MultipartFile file) {
        try {
            // 上传并返回访问地址
            final var url = sysFileService.uploadFile(file);
            final var sysFile = new SysFile();
            sysFile.setName(fileComponent.getName(url));
            sysFile.setUrl(url);
            return CommonResponse.success(sysFile);
        } catch (Exception e) {
            log.error("上传文件失败", e);
            return CommonResponse.error(e.getMessage());
        }
    }
}