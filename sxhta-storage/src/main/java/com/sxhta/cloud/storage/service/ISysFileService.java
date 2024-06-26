package com.sxhta.cloud.storage.service;

import com.sxhta.cloud.remote.vo.FileMetaVo;
import jakarta.annotation.Nullable;
import org.springframework.web.multipart.MultipartFile;

/**
 * 文件上传接口
 */
public interface ISysFileService {
    /**
     * 文件上传接口
     *
     * @param file 上传的文件
     * @return 访问地址
     */
    String uploadFile(MultipartFile file, @Nullable String folder) throws Exception;

    FileMetaVo getFileMeta();
}
