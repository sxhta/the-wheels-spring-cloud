package com.sxhta.cloud.remote.filter;

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

    public String filter(String target, String path) {
        final var result = "".contains(path);
        if (result) {
            return target;
        }
        final var fileMetaVo = attachmentService.getFileMetaVo();
        final var prefix = fileMetaVo.getPrefix();
        //noinspection HttpUrlsUsage
        final var isUrl = target.contains("http://") || target.contains("https://");
        //根据需要处理返回值
        if (target.contains(prefix) && !target.contains(IMAGE_BASE64) && !isUrl) {

            final var domain = fileMetaVo.getDomain();
            //此时是"/statics"
            target = attachmentService.addPrefix(target, domain, prefix);
        }
        return target;
    }
}
