package com.sxhta.cloud.remote.request;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;
import java.io.Serializable;

/**
 * 文件信息
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class FileUploadParamsRequest implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 自定义地址
     */
    private String path;
}
