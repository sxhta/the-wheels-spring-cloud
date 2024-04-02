package com.sxhta.cloud.common.constant;

import java.io.Serial;
import java.io.Serializable;

/**
 * Token的Key常量
 */
public final class TokenConstants implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 令牌自定义标识
     */
    public static final String AUTHENTICATION = "Authorization";

    /**
     * 令牌前缀
     */
    public static final String PREFIX = "Bearer ";

    /**
     * 令牌秘钥
     * sha512 spring-cloud-authorization
     * HMAC key sxhta
     */
    public final static String SECRET = "184831e6c577efcbbdb58e51f3f1fec4cf1849d601805b389806ccb465ee129de84d719df4d04d80fe0950de70d6264c9f1603fe65308f4f895a5f9a415c1111";
}
