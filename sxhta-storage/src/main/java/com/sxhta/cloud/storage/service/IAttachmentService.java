package com.sxhta.cloud.storage.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.sxhta.cloud.storage.entity.SystemAttachment;

/**
 * SystemAttachmentService 接口

 */
public interface IAttachmentService extends IService<SystemAttachment> {

    /**
     * 给图片加前缀
     * @param path String 路径
     * @return String
     */
    String prefixImage(String path);

    /**
     * 给文件加前缀
     * @param path String 路径
     * @return String
     */
    String prefixFile(String path);

    /**
     * 清除 cdn url， 在保存数据的时候使用
     * @param path String 文件路径
     * @return String
     */
    String clearPrefix(String path);
}
