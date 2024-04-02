package com.sxhta.cloud.common.xss;


import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.apache.commons.lang3.StringUtils;

import java.util.regex.Pattern;

/**
 * 自定义xss校验注解实现
 */
public class XssValidator implements ConstraintValidator<Xss, String> {
    private static final String HTML_PATTERN = "<(\\S*?)[^>]*>.*?|<.*? />";

    @Override
    public boolean isValid(String value, ConstraintValidatorContext constraintValidatorContext) {
        if (StringUtils.isBlank(value)) {
            return true;
        }
        return !containsHtml(value);
    }

    public static Boolean containsHtml(String value) {
        final var pattern = Pattern.compile(HTML_PATTERN);
        final var matcher = pattern.matcher(value);
        return matcher.matches();
    }
}