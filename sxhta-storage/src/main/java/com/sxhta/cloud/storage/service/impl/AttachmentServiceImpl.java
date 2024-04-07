package com.sxhta.cloud.storage.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sxhta.cloud.storage.entity.SystemAttachment;
import com.sxhta.cloud.storage.mapper.AttachmentMapper;
import com.sxhta.cloud.storage.service.IAttachmentService;
import jakarta.inject.Singleton;
import org.springframework.stereotype.Service;

@Singleton
@Service
public class AttachmentServiceImpl extends ServiceImpl<AttachmentMapper, SystemAttachment> implements IAttachmentService {

    @Override
    public String prefixImage(String path) {
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
