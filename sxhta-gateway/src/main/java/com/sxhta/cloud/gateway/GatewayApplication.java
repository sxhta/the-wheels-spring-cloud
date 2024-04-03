package com.sxhta.cloud.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableAsync;

import java.io.Serial;
import java.io.Serializable;

/**
 * 网关启动程序
 * DataSourceAutoConfiguration 排除数据库以来，网关不需要额外数据库存储
 */
@EnableAsync
//有了这个就不需要META-INF了
@ComponentScan(basePackages = {"com.sxhta.cloud"})
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class GatewayApplication implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    public static void main(String[] args) {
        SpringApplication.run(GatewayApplication.class, args);
    }
}
