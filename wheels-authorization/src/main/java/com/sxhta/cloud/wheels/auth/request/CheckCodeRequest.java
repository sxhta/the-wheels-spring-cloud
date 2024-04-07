package com.sxhta.cloud.wheels.auth.request;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serial;
import java.io.Serializable;

@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
public class CheckCodeRequest implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private String phone;

    private Integer code;
}
