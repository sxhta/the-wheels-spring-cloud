package com.wheels.cloud.frontend.component;

import com.sxhta.cloud.remote.vo.FileMetaVo;

/**
 * 这里只需要启动的时候获取OpenFeign信息即可，不需要每次请求都获取
 */
public interface FileMetaComponent {

    FileMetaVo getFileMetaVo();
}
