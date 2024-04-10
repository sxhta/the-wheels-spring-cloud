package com.sxhta.cloud.common.component.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sxhta.cloud.common.component.ServletComponent;
import com.sxhta.cloud.common.text.Convert;
import com.sxhta.cloud.common.utils.CommonStringUtil;
import com.sxhta.cloud.common.web.domain.CommonResponse;
import jakarta.inject.Singleton;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedCaseInsensitiveMap;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import reactor.core.publisher.Mono;

import java.io.IOException;
import java.io.Serial;
import java.io.Serializable;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * 客户端工具类
 */
@Singleton
@Component
public class ServletComponentImpl implements ServletComponent, Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 获取String参数
     */
    @Override
    public String getParameter(String name) {
        return getRequest().getParameter(name);
    }

    /**
     * 获取String参数
     */
    @Override
    public String getParameter(String name, String defaultValue) {
        return Convert.toStr(getRequest().getParameter(name), defaultValue);
    }

    /**
     * 获取Integer参数
     */
    @Override
    public Integer getParameterToInt(String name) {
        return Convert.toInt(getRequest().getParameter(name));
    }

    /**
     * 获取Integer参数
     */
    @Override
    public Integer getParameterToInt(String name, Integer defaultValue) {
        return Convert.toInt(getRequest().getParameter(name), defaultValue);
    }

    /**
     * 获取Boolean参数
     */
    @Override
    public Boolean getParameterToBool(String name) {
        return Convert.toBool(getRequest().getParameter(name));
    }

    /**
     * 获取Boolean参数
     */
    @Override
    public Boolean getParameterToBool(String name, Boolean defaultValue) {
        return Convert.toBool(getRequest().getParameter(name), defaultValue);
    }

    /**
     * 获得所有请求参数
     *
     * @param request 请求对象{@link ServletRequest}
     * @return Map
     */
    @Override
    public Map<String, String[]> getParams(ServletRequest request) {
        final var map = request.getParameterMap();
        return Collections.unmodifiableMap(map);
    }

    /**
     * 获得所有请求参数
     *
     * @param request 请求对象{@link ServletRequest}
     * @return Map
     */
    @Override
    public Map<String, String> getParamMap(ServletRequest request) {
        final var params = new HashMap<String, String>();
        for (final var entry : getParams(request).entrySet()) {
            params.put(entry.getKey(), StringUtils.join(entry.getValue(), ","));
        }
        return params;
    }

    /**
     * 获取request
     */
    @Override
    public HttpServletRequest getRequest() {
        try {
            return getRequestAttributes().getRequest();
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 获取response
     */
    @Override
    public HttpServletResponse getResponse() {
        try {
            return getRequestAttributes().getResponse();
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 获取session
     */
    @Override
    public HttpSession getSession() {
        return getRequest().getSession();
    }

    @Override
    public ServletRequestAttributes getRequestAttributes() {
        try {
            final var attributes = RequestContextHolder.getRequestAttributes();
            return (ServletRequestAttributes) attributes;
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public String getHeader(HttpServletRequest request, String name) {
        final var value = request.getHeader(name);
        if (StringUtils.isEmpty(value)) {
            return StringUtils.EMPTY;
        }
        return urlDecode(value);
    }

    @Override
    public Map<String, String> getHeaders(HttpServletRequest request) {
        final Map<String, String> map = new LinkedCaseInsensitiveMap<>();
        final var enumeration = request.getHeaderNames();
        if (enumeration != null) {
            while (enumeration.hasMoreElements()) {
                final var key = enumeration.nextElement();
                final var value = request.getHeader(key);
                map.put(key, value);
            }
        }
        return map;
    }

    /**
     * 将字符串渲染到客户端
     *
     * @param response 渲染对象
     * @param string   待渲染的字符串
     */
    @SuppressWarnings("CallToPrintStackTrace")
    @Override
    public void renderString(HttpServletResponse response, String string) {
        try {
            response.setStatus(200);
            response.setContentType("application/json");
            response.setCharacterEncoding("utf-8");
            response.getWriter().print(string);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 是否是Ajax异步请求
     */
    @Override
    public Boolean isAjaxRequest(HttpServletRequest request) {
        final var accept = request.getHeader("accept");
        if (accept != null && accept.contains("application/json")) {
            return true;
        }

        final var xRequestedWith = request.getHeader("X-Requested-With");
        if (xRequestedWith != null && xRequestedWith.contains("XMLHttpRequest")) {
            return true;
        }

        final var uri = request.getRequestURI();
        if (CommonStringUtil.inStringIgnoreCase(uri, ".json", ".xml")) {
            return true;
        }

        final var ajax = request.getParameter("__ajax");
        return CommonStringUtil.inStringIgnoreCase(ajax, "json", "xml");
    }

    /**
     * 内容编码
     *
     * @param str 内容
     * @return 编码后的内容
     */
    @Override
    public String urlEncode(String str) {
        return URLEncoder.encode(str, StandardCharsets.UTF_8);
    }

    /**
     * 内容解码
     *
     * @param str 内容
     * @return 解码后的内容
     */
    @Override
    public String urlDecode(String str) {
        return URLDecoder.decode(str, StandardCharsets.UTF_8);
    }

    /**
     * 设置webflux模型响应
     *
     * @param response ServerHttpResponse
     * @param value    响应内容
     * @return Mono<Void>
     */
    @Override
    public Mono<Void> webFluxResponseWriter(ServerHttpResponse response, Object value) {
        return webFluxResponseWriter(response, HttpStatus.OK, value, HttpStatus.INTERNAL_SERVER_ERROR.value());
    }

    /**
     * 设置webflux模型响应
     *
     * @param response ServerHttpResponse
     * @param code     响应状态码
     * @param value    响应内容
     * @return Mono<Void>
     */
    @Override
    public Mono<Void> webFluxResponseWriter(ServerHttpResponse response, Object value, Integer code) {
        return webFluxResponseWriter(response, HttpStatus.OK, value, code);
    }

    /**
     * 设置webflux模型响应
     *
     * @param response ServerHttpResponse
     * @param status   http状态码
     * @param code     响应状态码
     * @param value    响应内容
     * @return Mono<Void>
     */
    @Override
    public Mono<Void> webFluxResponseWriter(ServerHttpResponse response, HttpStatus status, Object value, Integer code) {
        return webFluxResponseWriter(response, MediaType.APPLICATION_JSON_VALUE, status, value, code);
    }

    /**
     * 设置webflux模型响应
     *
     * @param response    ServerHttpResponse
     * @param contentType content-type
     * @param status      http状态码
     * @param code        响应状态码
     * @param value       响应内容
     * @return Mono<Void>
     */
    @Override
    public Mono<Void> webFluxResponseWriter(ServerHttpResponse response, String contentType, HttpStatus status, Object value, Integer code) {
        response.setStatusCode(status);
        response.getHeaders().add(HttpHeaders.CONTENT_TYPE, contentType);
        final var result = CommonResponse.error(code, value.toString());
        final var objectMapper = new ObjectMapper();
        try {
            final var jsonByts = objectMapper.writeValueAsBytes(result);
//            final var dataBuffer = response.bufferFactory().wrap(JSON.toJSONString(result).getBytes());
            final var dataBuffer = response.bufferFactory().wrap(jsonByts);
            return response.writeWith(Mono.just(dataBuffer));
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

    }
}
