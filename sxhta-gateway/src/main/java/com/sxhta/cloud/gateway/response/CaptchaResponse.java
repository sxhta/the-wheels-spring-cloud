package com.sxhta.cloud.gateway.response;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serial;
import java.io.Serializable;

@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
public final class CaptchaResponse implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private Boolean captchaEnabled;

    private String uuid;

    private String img;
}