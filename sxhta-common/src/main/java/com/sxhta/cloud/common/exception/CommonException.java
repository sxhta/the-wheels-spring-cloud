package com.sxhta.cloud.common.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.io.Serial;

/**
 * 业务异常
 */
@Slf4j
@RestControllerAdvice
public final class CommonException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = 1L;

    public CommonException() {
    }

    public CommonException(String message) {
        super(message);
    }
}