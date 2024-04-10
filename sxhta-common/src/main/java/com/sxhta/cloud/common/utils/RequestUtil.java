package com.sxhta.cloud.common.utils;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

/**
 * Request工具类
 */
public class RequestUtil extends HttpServlet {
    public static HttpServletRequest getRequest() {
        if (RequestContextHolder.getRequestAttributes() != null) {
            return ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes())).getRequest();
        }

        return null;
    }

    public static HashMap<String, Object> getRequestParamAndHeader() {

        try {
            final var request = getRequest();
            if (request == null) {
                return null;
            }

            //request信息
            final var data = new HashMap<String, Object>();
            final var requestParams = new HashMap<String, Object>();
            final var paraNames = request.getParameterNames();
            if (paraNames != null) {
                while (paraNames.hasMoreElements()) {
                    final var key = paraNames.nextElement();
                    requestParams.put(key, request.getParameter(key));
                }
            }

            final var requestFilter = new HashMap<String, Object>();
            final var attributeNames = request.getAttributeNames();
            if (attributeNames != null) {
                while (attributeNames.hasMoreElements()) {
                    String key = attributeNames.nextElement();
                    if (key.contains("request_")) {
                        requestFilter.put(key, request.getAttribute(key));
                    }
                }
            }

            data.put("url", request.getRequestURL());
            data.put("uri", request.getRequestURI());
            data.put("method", request.getMethod());
            data.put("request", requestParams);
            data.put("request_filter", requestFilter);

            //header 信息
            final var headerNames = request.getHeaderNames();
            final var headerParams = new HashMap<String, Object>();
            if (headerNames != null) {
                while (headerNames.hasMoreElements()) {
                    final var key = headerNames.nextElement();
                    final var value = request.getHeader(key);
                    headerParams.put(key, value);
                }
            }
            data.put("header", headerParams);


            return data;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String getDomain() {
        final var request = getRequest();
        if (request == null) {
            return null;
        }
        return request.getServerName() + ":" + request.getServerPort();
    }

    public static String getUri(HttpServletRequest request) {
        final var uri = request.getRequestURI();
        final var list = stringToArrayStrRegex(uri, "/");
        list.removeIf(StringUtils::isNumeric); //去掉url中的数字参数
        list.removeIf(c -> c.contains(","));// 去掉url中的逗号分隔参数
        return StringUtils.join(list, "/");
    }

    public static List<String> stringToArrayStrRegex(String str, String regex) {
        final var list = new ArrayList<String>();
        if (str.contains(regex)) {

            final var split = str.split(regex);

            for (final var value : split) {
                if (!StringUtils.isBlank(value)) {
                    list.add(value);
                }
            }
        } else {
            list.add(str);
        }
        return list;
    }
}
