package com.sxhta.cloud.common.text;


import com.sxhta.cloud.common.utils.CommonStringUtil;

import java.io.Serial;
import java.io.Serializable;

/**
 * 字符串格式化
 */
public final class StrFormatter implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    public static final String EMPTY_JSON = "{}";
    public static final char C_BACKSLASH = '\\';
    public static final char C_DELIM_START = '{';
    public static final char C_DELIM_END = '}';

    /**
     * 格式化字符串<br>
     * 此方法只是简单将占位符 {} 按照顺序替换为参数<br>
     * 如果想输出 {} 使用 \\转义 { 即可，如果想输出 {} 之前的 \ 使用双转义符 \\\\ 即可<br>
     * 例：<br>
     * 通常使用：format("this is {} for {}", "a", "b") -> this is a for b<br>
     * 转义{}： format("this is \\{} for {}", "a", "b") -> this is \{} for a<br>
     * 转义\： format("this is \\\\{} for {}", "a", "b") -> this is \a for b<br>
     *
     * @param strPattern 字符串模板
     * @param argArray   参数列表
     * @return 结果
     */
    public static String format(final String strPattern, final Object... argArray) {
        if (CommonStringUtil.isEmpty(strPattern) || CommonStringUtil.isEmpty(argArray)) {
            return strPattern;
        }
        final var strPatternLength = strPattern.length();

        // 初始化定义好的长度以获得更好的性能
        final var stringBuilder = new StringBuilder(strPatternLength + 50);

        var handledPosition = 0;
        Integer delimIndex;// 占位符所在位置
        for (var argIndex = 0; argIndex < argArray.length; argIndex++) {
            delimIndex = strPattern.indexOf(EMPTY_JSON, handledPosition);
            if (delimIndex == -1) {
                if (handledPosition == 0) {
                    return strPattern;
                } else { // 字符串模板剩余部分不再包含占位符，加入剩余部分后返回结果
                    stringBuilder.append(strPattern, handledPosition, strPatternLength);
                    return stringBuilder.toString();
                }
            } else {
                if (delimIndex > 0 && strPattern.charAt(delimIndex - 1) == C_BACKSLASH) {
                    if (delimIndex > 1 && strPattern.charAt(delimIndex - 2) == C_BACKSLASH) {
                        // 转义符之前还有一个转义符，占位符依旧有效
                        stringBuilder.append(strPattern, handledPosition, delimIndex - 1);
                        stringBuilder.append(Convert.utf8Str(argArray[argIndex]));
                        handledPosition = delimIndex + 2;
                    } else {
                        // 占位符被转义
                        argIndex--;
                        stringBuilder.append(strPattern, handledPosition, delimIndex - 1);
                        stringBuilder.append(C_DELIM_START);
                        handledPosition = delimIndex + 1;
                    }
                } else {
                    // 正常占位符
                    stringBuilder.append(strPattern, handledPosition, delimIndex);
                    stringBuilder.append(Convert.utf8Str(argArray[argIndex]));
                    handledPosition = delimIndex + 2;
                }
            }
        }
        // 加入最后一个占位符后所有的字符
        stringBuilder.append(strPattern, handledPosition, strPattern.length());

        return stringBuilder.toString();
    }
}
