package com.sxhta.cloud.gateway.filter;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

@Data
public final class CacheRequestFilterConfig implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private Integer order;
}
