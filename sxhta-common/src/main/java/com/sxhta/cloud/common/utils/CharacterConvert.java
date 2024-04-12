package com.sxhta.cloud.common.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class CharacterConvert implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 将字符串转换为Json数组
     *
     * @param str 字符串
     * @return Json数组
     */
    public static ArrayList stringToJsonList(String str) {
        final var objectMapper = new ObjectMapper();
        try {
            return objectMapper.readValue(str, ArrayList.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 将Json数组转换成字符串
     *
     * @param list Json数组
     * @return 字符串
     */
    public static String listToJsonString(List<String> list) {
        final var objectMapper = new ObjectMapper();
        try {
            return objectMapper.writeValueAsString(list);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }


}
