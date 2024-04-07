package com.sxhta.cloud.storage.config;

import jakarta.inject.Inject;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.io.File;
import java.io.Serial;
import java.io.Serializable;

/**
 * 通用映射配置
 */
@Configuration
public class ResourcesConfig implements WebMvcConfigurer, Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Inject
    private LocalFileConfig localFilePath;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        final var localFilePrefix = localFilePath.getPrefix();
        /* 本地文件上传路径 */
        registry.addResourceHandler(localFilePrefix + "/**")
                .addResourceLocations("file:" + localFilePath.getPath() + File.separator);
    }

    /**
     * 开启跨域
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        final var localFilePrefix = localFilePath.getPrefix();
        // 设置允许跨域的路由
        registry.addMapping(localFilePrefix + "/**")
                // 设置允许跨域请求的域名
                .allowedOrigins("*")
                // 设置允许的方法
                .allowedMethods("GET");
    }
}