package com.sxhta.cloud.remote.filter;

import cn.hutool.core.util.ObjectUtil;
import com.sxhta.cloud.remote.service.AttachmentService;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import org.springframework.stereotype.Component;

import java.io.Serial;
import java.io.Serializable;

/**
 * response路径处理
 */
@Singleton
@Component
public final class ResponseRouter implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Inject
    private AttachmentService attachmentService;

    private static final String IMAGE_BASE64 = "data:image/png;base64";

    public String filter(String data, String path) {
        final var result = "".contains(path);
        if (result) {
            return data;
        }
        final var fileMetaVo = attachmentService.getFileMetaVo();
        if (ObjectUtil.isNotNull(fileMetaVo)) {
            final var prefix = fileMetaVo.getPrefix();
            //根据需要处理返回值
            if (data.contains(prefix) && !data.contains(IMAGE_BASE64)) {
                final var domain = fileMetaVo.getDomain();
                //此时是"/statics"
                data = attachmentService.addPrefix(data, domain, prefix);
            }
        }
        return data;
    }
}
