package com.sxhta.cloud.common.constant;

import java.io.Serial;
import java.io.Serializable;

/**
 * 服务名称
 */
public final class ServiceNameConstants implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 认证服务的serviceId
     */
    public static final String AUTH_SERVICE = "sxhta-authorization";

    /**
     * 系统模块的serviceId
     */
    public static final String SYSTEM_SERVICE = "sxhta-system-backend";

    /**
     * 文件服务的serviceId
     */
    public static final String FILE_SERVICE = "sxhta-storage";

    /**
     * 打车前端的serviceId
     */
    public static final String WHEELS_FRONTEND = "wheels-frontend";
}
