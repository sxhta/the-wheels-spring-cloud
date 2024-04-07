package com.sxhta.cloud.wheels.remote.request;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serial;
import java.io.Serializable;

@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
public final class RegisterRequest implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private String account;
}