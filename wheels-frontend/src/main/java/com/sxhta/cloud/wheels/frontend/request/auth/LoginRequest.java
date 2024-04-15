package com.sxhta.cloud.wheels.frontend.request.auth;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serial;
import java.io.Serializable;

@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
public final class LoginRequest implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private String account;

    private String password;
}