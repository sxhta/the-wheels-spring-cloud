package com.wheels.cloud.order.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serial;
import java.io.Serializable;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class LoginResponse implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private Long userId;

    private String account;

    private String realName;

    @JsonProperty(value = "access_token")
    private String accessToken;

    @JsonProperty(value = "expires_in")
    private Long expiresIn;

    /**
     * 登录时间
     */
    private Long loginTime;

    /**
     * 过期时间
     */
    private Long expireTime;
}