package com.wheels.cloud.frontend;

import com.sxhta.cloud.security.annotation.EnableCustomConfig;
import com.sxhta.cloud.security.annotation.EnableCustomFeignClients;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * 三轮车前台
 */
@EnableCustomConfig
@EnableCustomFeignClients
@SpringBootApplication
@MapperScan("com.wheels.cloud.**.mapper")
@EnableFeignClients(basePackages = "com.sxhta.cloud")
public class WheelsFrontendApplication {
    public static void main(String[] args) {

        SpringApplication.run(WheelsFrontendApplication.class, args);
    }
}
