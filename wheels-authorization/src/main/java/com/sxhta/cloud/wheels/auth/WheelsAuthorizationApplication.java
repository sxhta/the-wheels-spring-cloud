package com.sxhta.cloud.wheels.auth;

import com.sxhta.cloud.security.annotation.EnableCustomConfig;
import com.sxhta.cloud.security.annotation.EnableCustomFeignClients;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.openfeign.EnableFeignClients;

import java.io.Serial;
import java.io.Serializable;

/**
 * 认证授权中心
 */
@EnableCustomConfig
@EnableCustomFeignClients
@EnableFeignClients(basePackages = "com.wheels.cloud")
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class WheelsAuthorizationApplication implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    public static void main(String[] args) {
        SpringApplication.run(WheelsAuthorizationApplication.class, args);
    }
}