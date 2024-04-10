package com.sxhta.cloud.storage.controller;

import cn.hutool.core.util.ObjectUtil;
import com.sxhta.cloud.common.component.FileComponent;
import com.sxhta.cloud.common.web.domain.CommonResponse;
import com.sxhta.cloud.remote.domain.SysFile;
import com.sxhta.cloud.remote.vo.FileMetaVo;
import com.sxhta.cloud.storage.service.ISysFileService;
import jakarta.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * 文件请求处理
 *
 * @author ruoyi
 */
@RestController
public class FileUploadController {
    private static final Logger log = LoggerFactory.getLogger(FileUploadController.class);

    @Inject
    private ISysFileService sysFileService;

    @Inject
    private FileComponent fileComponent;

    /**
     * 文件上传请求
     */
    @PostMapping("upload")
    public CommonResponse<SysFile> upload(@RequestPart("file") MultipartFile file, @RequestParam String folder) {
        try {
            // 上传并返回访问地址
            final var url = sysFileService.uploadFile(file, folder);
            final var sysFile = new SysFile();
            sysFile.setName(fileComponent.getName(url));
            sysFile.setUrl(url);
            return CommonResponse.success("上传成功", sysFile);
        } catch (Exception e) {
            log.error("上传文件失败", e);
            return CommonResponse.error(e.getMessage());
        }
    }

    /**
     * 获取文件存储信息
     */
    @GetMapping("meta")
    public CommonResponse<FileMetaVo> getMeta() {
        final var meta = sysFileService.getFileMeta();
        if(ObjectUtil.isNull(meta)){
            CommonResponse.error("文件存储信息获取失败");
        }
        return CommonResponse.success(meta);
    }
}