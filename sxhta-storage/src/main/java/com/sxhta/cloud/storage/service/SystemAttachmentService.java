package com.sxhta.cloud.storage.service;


public interface SystemAttachmentService {

    /**
     * 给图片加前缀
     *
     * @param fileUrl 文件路径
     * @return String
     */
    String prefixImage(String fileUrl);

    /**
     * 给文件加前缀
     *
     * @param path String 路径
     * @return String
     */
    String prefixFile(String path);
}
