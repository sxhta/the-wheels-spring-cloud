package com.sxhta.cloud.storage.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sxhta.cloud.configuration.config.ConfigKeys;
import com.sxhta.cloud.configuration.service.SysConfigService;
import com.sxhta.cloud.storage.entity.SystemAttachment;
import com.sxhta.cloud.storage.mapper.AttachmentMapper;
import com.sxhta.cloud.storage.service.IAttachmentService;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import org.springframework.stereotype.Service;

@Singleton
@Service
public class AttachmentServiceImpl extends ServiceImpl<AttachmentMapper, SystemAttachment> implements IAttachmentService {

    @Inject
    private SysConfigService sysConfigService;


    @Override
    public String prefixImage(String path) {
        final var host = sysConfigService.selectConfigByKey(ConfigKeys.FILE_HOST);

        return "";
    }

    @Override
    public String prefixFile(String path) {
        return "";
    }

    @Override
    public String clearPrefix(String path) {
        return "";
    }
}
