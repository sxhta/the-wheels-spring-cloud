package com.sxhta.cloud.gateway.config;

import com.google.code.kaptcha.text.impl.DefaultTextCreator;
import jakarta.inject.Singleton;
import org.springframework.context.annotation.Configuration;

import java.io.Serial;
import java.io.Serializable;
import java.util.Random;

/**
 * 验证码文本生成器
 */
@Singleton
@Configuration
public class KaptchaTextCreator extends DefaultTextCreator implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private static final String[] NUMBERS = "0,1,2,3,4,5,6,7,8,9,10".split(",");

    @Override
    public String getText() {
        Integer result;
        final var random = new Random();
        final var x = random.nextInt(10);
        final var y = random.nextInt(10);
        final var suChinese = new StringBuilder();
        final var randomoperands = random.nextInt(3);
        if (randomoperands == 0) {
            result = x * y;
            suChinese.append(NUMBERS[x]);
            suChinese.append("*");
            suChinese.append(NUMBERS[y]);
        } else if (randomoperands == 1) {
            if ((x != 0) && y % x == 0) {
                result = y / x;
                suChinese.append(NUMBERS[y]);
                suChinese.append("/");
                suChinese.append(NUMBERS[x]);
            } else {
                result = x + y;
                suChinese.append(NUMBERS[x]);
                suChinese.append("+");
                suChinese.append(NUMBERS[y]);
            }
        } else {
            if (x >= y) {
                result = x - y;
                suChinese.append(NUMBERS[x]);
                suChinese.append("-");
                suChinese.append(NUMBERS[y]);
            } else {
                result = y - x;
                suChinese.append(NUMBERS[y]);
                suChinese.append("-");
                suChinese.append(NUMBERS[x]);
            }
        }
        suChinese.append("=?@").append(result);
        return suChinese.toString();
    }
}