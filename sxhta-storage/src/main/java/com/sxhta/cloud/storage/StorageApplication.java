package com.sxhta.cloud.storage;

import com.sxhta.cloud.security.annotation.EnableCustomConfig;
import com.sxhta.cloud.security.annotation.EnableCustomFeignClients;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.scheduling.annotation.EnableAsync;

/**
 * 文件服务
 */
@EnableAsync
@EnableCustomConfig
@EnableCustomFeignClients
@EnableFeignClients(basePackages = "com.sxhta.cloud")
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class StorageApplication {
    public static void main(String[] args) {
        SpringApplication.run(StorageApplication.class, args);
    }
}
