package com.sxhta.cloud.storage.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.io.Serial;
import java.io.Serializable;

@Data
@Configuration
@ConfigurationProperties(prefix = "file")
public class LocalFileConfig implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private String path;

    public String prefix;

    public String domain;
}
