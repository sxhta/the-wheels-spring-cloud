package com.sxhta.cloud.security.annotation;

import com.sxhta.cloud.security.config.ApplicationConfiguration;
import com.sxhta.cloud.security.feign.FeignAutoConfiguration;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.Import;
import org.springframework.scheduling.annotation.EnableAsync;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
// 表示通过aop框架暴露该代理对象,AopContext能够访问
@EnableAspectJAutoProxy(exposeProxy = true)
// 指定要扫描的Mapper类的包的路径
@MapperScan("com.sxhta.cloud.**.mapper")
// 开启线程异步执行
@EnableAsync
//有了这个就不需要META-INF了
@ComponentScan(basePackages = {"com.sxhta.cloud"})
// 自动加载类
@Import({ApplicationConfiguration.class, FeignAutoConfiguration.class})
public @interface EnableCustomConfig {

}
