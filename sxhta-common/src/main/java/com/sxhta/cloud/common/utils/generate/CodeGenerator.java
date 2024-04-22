package com.sxhta.cloud.common.utils.generate;


import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.OutputFile;
import com.baomidou.mybatisplus.generator.config.builder.CustomFile;

import java.util.*;

/**
 * mp代码生成器
 */
public class CodeGenerator {

    public static void main(String[] args) {
        generate();
    }

    //    private static final String PARENT_PATH = "com.sxhta.cloud.wheels";//后管
    private static final String PARENT_PATH = "com.sxhta.cloud.wheels.frontend";//订单模块

    private static void generate() {
        //
        FastAutoGenerator.create("jdbc:mysql://192.168.31.219:3306/the_wheels_cloud?serverTimezone=GMT%2b8", "root", "root@local")
                .globalConfig(builder -> {
                    builder.author("") // 设置作者
                            .enableSwagger() // 开启 swagger 模式
                            .outputDir("D:\\xiangmu\\the-wheels-spring-cloud\\wheels-frontend\\src\\main\\java\\"); // 后管
                })
                .packageConfig(builder -> {
                    builder.parent(PARENT_PATH) // 设置父包名
                            .moduleName(null) // 设置父包模块名
                            .entity("entity.routeconfig")//实体所在的包名
                            .service("service.routeconfig")
                            .serviceImpl("service.routeconfig.impl")
                            .controller("controller.routeconfig")
                            .mapper("mapper.routeconfig")
                            .pathInfo(Collections.singletonMap(OutputFile.xml, "D:\\xiangmu\\the-wheels-spring-cloud\\wheels-frontend\\src\\main\\resources\\mapper\\routeconfig\\")); // 后管
                })
                .strategyConfig(builder -> {
                    builder.serviceBuilder().formatServiceFileName("%sService").formatServiceImplFileName("%sServiceImpl");
                    builder.entityBuilder().enableLombok();
//                    builder.mapperBuilder().enableMapperAnnotation().build();
                    builder.controllerBuilder().enableHyphenStyle()  // 开启驼峰转连字符
                            .enableRestStyle();  // 开启生成@RestController 控制器
                    builder.addInclude("wheels_area", "wheels_route", "wheels_other_fee") // 设置需要生成的表名
                            .addTablePrefix("item_", "sys_", "unify_", "wheels_"); // 设置过滤表前缀
                })
                .injectionConfig(consumer -> {
                    Map<String, Object> customMap = new HashMap<>();
                    customMap.put("response", PARENT_PATH + ".response");
                    customMap.put("request", PARENT_PATH + ".request");

                    consumer.customMap(customMap);
                    // DTO
                    List<CustomFile> customFiles = new ArrayList<>();
                    customFiles.add(new CustomFile.Builder().packageName("response").fileName("Response.java")
                            .templatePath("/templates/response.java.vm").enableFileOverride().build());
                    customFiles.add(new CustomFile.Builder().packageName("request").fileName("Request.java")
                            .templatePath("/templates/request.java.vm").enableFileOverride().build());
                    customFiles.add(new CustomFile.Builder().packageName("request").fileName("SearchRequest.java")
                            .templatePath("/templates/searchrequest.java.vm").enableFileOverride().build());
                    consumer.customFile(customFiles);
                })
                .execute();

    }
}
