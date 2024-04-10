package com.sxhta.cloud.common.service;


public interface AttachmentService {

    /**
     * 给图片加前缀
     *
     * @param fileUrl 文件路径
     * @return String
     */
    String clearPrefix(String fileUrl);

    /**
     * 给文件加前缀
     *
     * @param target String 路径
     * @return String
     */
    String addPrefix(String target, String domain, String prefix);
}
