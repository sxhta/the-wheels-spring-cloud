package com.sxhta.cloud.gateway.config.properties;

import jakarta.inject.Singleton;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Configuration;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * XSS跨站脚本配置
 */
@Data
@Singleton
@Configuration
@RefreshScope
@ConfigurationProperties(prefix = "security.xss")
public class XssProperties implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * Xss开关
     */
    private Boolean enabled;

    /**
     * 排除路径
     */
    private List<String> excludeUrls = new ArrayList<>();
}
