package com.wheels.cloud.order.utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class TimesUtils {
    //获取当前时间字符串
    public static String getCurrentTimeString() {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
        return now.format(formatter);
    }
}
