package com.sxhta.cloud.remote;

import com.sxhta.cloud.common.constant.ServiceNameConstants;
import com.sxhta.cloud.common.web.domain.CommonResponse;
import com.sxhta.cloud.remote.domain.SysFile;
import com.sxhta.cloud.remote.factory.RemoteFileFallbackFactory;
import com.sxhta.cloud.remote.vo.FileMetaVo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

/**
 * 文件服务
 */
@FeignClient(contextId = "remoteFileOpenFeign", value = ServiceNameConstants.FILE_SERVICE, fallbackFactory = RemoteFileFallbackFactory.class)
public interface RemoteFileOpenFeign {
    /**
     * 上传文件
     *
     * @param file 文件信息
     * @return 结果
     */
    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    CommonResponse<SysFile> upload(@RequestPart(value = "file") MultipartFile file, @RequestParam(value = "folder") String path);

    @GetMapping(value = "/meta")
    CommonResponse<FileMetaVo> getFileMeta();
}
