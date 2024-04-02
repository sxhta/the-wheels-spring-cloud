package com.sxhta.cloud.common.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.io.Serial;

/**
 * 业务空指针异常
 */
@Slf4j
@RestControllerAdvice
public final class CommonNullException extends NullPointerException {

    @Serial
    private static final long serialVersionUID = 1L;

    public CommonNullException() {
    }

    public CommonNullException(String message) {
        super(message);
    }
}