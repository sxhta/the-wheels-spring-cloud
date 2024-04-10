package com.sxhta.cloud.wheels.remote.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;
import java.io.Serializable;

/**
 * 文件信息
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class SysFile implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 文件名称
     */
    private String name;

    /**
     * 文件地址
     */
    private String url;
}
