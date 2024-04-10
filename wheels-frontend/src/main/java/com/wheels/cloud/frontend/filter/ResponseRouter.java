package com.wheels.cloud.frontend.filter;

import com.sxhta.cloud.common.service.AttachmentService;
import com.wheels.cloud.frontend.component.FileMetaComponent;
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

    /**
     * 这里只需要启动的时候获取OpenFeign信息即可，不需要每次请求都获取
     */
    @Inject
    private FileMetaComponent fileMetaComponent;

    public String filter(String data, String path) {
        final var result = "".contains(path);
        if (result) {
            return data;
        }
        final var fileMetaVo = fileMetaComponent.getFileMetaVo();
        final var prefix = fileMetaVo.getPrefix();
        //此时是statics/，prefix是/statics
        final var resultPrefix = prefix.replaceFirst("/", "") + "/";
        //根据需要处理返回值
        if (data.contains(resultPrefix) && !data.contains(IMAGE_BASE64)) {

            final var domain = fileMetaVo.getDomain();
            //此时是"/statics"
            data = attachmentService.addPrefix(data, domain, prefix);
        }
        return data;
    }
}
