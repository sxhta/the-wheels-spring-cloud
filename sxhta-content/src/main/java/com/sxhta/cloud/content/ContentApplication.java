package com.sxhta.cloud.content;

import com.sxhta.cloud.security.annotation.EnableCustomConfig;
import com.sxhta.cloud.security.annotation.EnableCustomFeignClients;
import com.sxhta.cloud.security.config.ApplicationConfiguration;
import com.sxhta.cloud.security.feign.FeignAutoConfiguration;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Import;

/**
 * 系统模块
 */
@EnableCustomConfig
@EnableCustomFeignClients
@SpringBootApplication
@MapperScan("com.sxhta.cloud.**.mapper")
@EnableFeignClients(basePackages = "com.sxhta.cloud")
@Import({ApplicationConfiguration.class, FeignAutoConfiguration.class})
public class ContentApplication {
    public static void main(String[] args) {
        SpringApplication.run(ContentApplication.class, args);
    }
}
