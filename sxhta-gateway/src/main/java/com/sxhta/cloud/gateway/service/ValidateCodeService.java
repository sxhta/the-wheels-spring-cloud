package com.sxhta.cloud.gateway.service;

import com.sxhta.cloud.common.exception.CaptchaException;
import com.sxhta.cloud.common.web.domain.CommonResponse;

import java.io.IOException;
import java.io.Serializable;

/**
 * 验证码处理
 */
public interface ValidateCodeService extends Serializable {

    /**
     * 生成验证码
     */
    CommonResponse createCaptcha() throws IOException, CaptchaException;

    /**
     * 校验验证码
     */
    void checkCaptcha(String key, String value) throws CaptchaException;
}
