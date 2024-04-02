package com.sxhta.cloud.common.exception.file;

import java.io.Serial;

/**
 * 文件名称超长限制异常类
 */
public class FileNameLengthLimitExceededException extends FileException {
    @Serial
    private static final long serialVersionUID = 1L;

    public FileNameLengthLimitExceededException(Integer defaultFileNameLength) {
        super("upload.filename.exceed.length", new Object[]{defaultFileNameLength}, "the filename is too long");
    }
}
