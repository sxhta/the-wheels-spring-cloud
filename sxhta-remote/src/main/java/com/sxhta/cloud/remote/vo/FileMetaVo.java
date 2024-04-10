package com.sxhta.cloud.remote.vo;


import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serial;
import java.io.Serializable;

/**
 * 用户信息
 * 存储在Redis中的用户信息
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
public class FileMetaVo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private String domain;

    private String path;

    private String prefix;
}