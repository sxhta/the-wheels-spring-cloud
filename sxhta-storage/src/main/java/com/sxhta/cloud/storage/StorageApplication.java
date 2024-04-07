package com.sxhta.cloud.storage;

import com.sxhta.cloud.security.annotation.EnableCustomConfig;
import com.sxhta.cloud.security.annotation.EnableCustomFeignClients;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

/**
 * 文件服务
 */
@EnableAsync
@EnableCustomConfig
@EnableCustomFeignClients
@SpringBootApplication
public class StorageApplication
{
    public static void main(String[] args)
    {
        SpringApplication.run(StorageApplication.class, args);
    }
}
