package com.sxhta.cloud.security.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serial;
import java.io.Serializable;

@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
public final class TokenResponse implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @JsonProperty(value = "access_token")
    private String accessToken;

    @JsonProperty(value = "expire_time")
    private Long expireTime;
}