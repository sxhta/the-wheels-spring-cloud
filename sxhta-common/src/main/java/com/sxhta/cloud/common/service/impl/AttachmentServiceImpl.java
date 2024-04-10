package com.sxhta.cloud.common.service.impl;

import com.sxhta.cloud.common.service.AttachmentService;
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

    /**
     * 给图片加前缀
     *
     * @param fileUrl 文件路径
     * @return String
     */
    @Override
    public String clearPrefix(String fileUrl) {
        return fileUrl.substring(fileUrl.indexOf("/") + 1);
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
        //此时是statics/，prefix是/statics
        final var resultPrefix = prefix.replaceFirst("/", "") + "/";
        return target.replace(resultPrefix, domain + "/" + "statics" + "/");
    }
}