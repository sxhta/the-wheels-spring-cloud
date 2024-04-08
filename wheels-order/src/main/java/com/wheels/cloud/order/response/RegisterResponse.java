package com.wheels.cloud.order.response;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serial;
import java.io.Serializable;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class RegisterResponse implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private Long userId;

    private String userHash;

    private String account;
}