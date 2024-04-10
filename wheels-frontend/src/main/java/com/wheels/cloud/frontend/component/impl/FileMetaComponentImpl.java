package com.wheels.cloud.frontend.component.impl;

import com.sxhta.cloud.remote.RemoteFileOpenFeign;
import com.sxhta.cloud.remote.vo.FileMetaVo;
import com.wheels.cloud.frontend.component.FileMetaComponent;
import jakarta.annotation.PostConstruct;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import lombok.EqualsAndHashCode;
import org.springframework.stereotype.Component;

import java.io.Serial;
import java.io.Serializable;

/**
 * 这里只需要启动的时候获取OpenFeign信息即可，不需要每次请求都获取
 */
@Singleton
@Component
@EqualsAndHashCode(callSuper = false)
public final class FileMetaComponentImpl implements FileMetaComponent, Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private FileMetaVo fileMetaVo;

    @Inject
    private RemoteFileOpenFeign remoteFileOpenFeign;

    @PostConstruct
    private void init() {
        final var remoteResponse = remoteFileOpenFeign.getFileMeta();
        this.fileMetaVo = remoteResponse.getData();
    }

    @Override
    public FileMetaVo getFileMetaVo() {
        return this.fileMetaVo;
    }
}