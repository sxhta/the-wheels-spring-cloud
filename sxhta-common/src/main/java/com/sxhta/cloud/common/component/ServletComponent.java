package com.sxhta.cloud.common.component;

import jakarta.servlet.ServletRequest;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.web.context.request.ServletRequestAttributes;
import reactor.core.publisher.Mono;

import java.util.Map;

public interface ServletComponent {

    /**
     * 获取String参数
     */
    String getParameter(String name);

    /**
     * 获取String参数
     */
    String getParameter(String name, String defaultValue);

    /**
     * 获取Integer参数
     */
    Integer getParameterToInt(String name);

    /**
     * 获取Integer参数
     */
    Integer getParameterToInt(String name, Integer defaultValue);

    /**
     * 获取Boolean参数
     */
    Boolean getParameterToBool(String name);

    /**
     * 获取Boolean参数
     */
    Boolean getParameterToBool(String name, Boolean defaultValue);

    /**
     * 获得所有请求参数
     *
     * @param request 请求对象{@link ServletRequest}
     * @return Map
     */
    Map<String, String[]> getParams(ServletRequest request);

    /**
     * 获得所有请求参数
     *
     * @param request 请求对象{@link ServletRequest}
     * @return Map
     */
    Map<String, String> getParamMap(ServletRequest request);

    /**
     * 获取request
     */
    HttpServletRequest getRequest();

    /**
     * 获取response
     */
    HttpServletResponse getResponse();

    /**
     * 获取session
     */
    HttpSession getSession();

    ServletRequestAttributes getRequestAttributes();

    String getHeader(HttpServletRequest request, String name);

    Map<String, String> getHeaders(HttpServletRequest request);

    /**
     * 将字符串渲染到客户端
     *
     * @param response 渲染对象
     * @param string   待渲染的字符串
     */
    void renderString(HttpServletResponse response, String string);

    /**
     * 是否是Ajax异步请求
     *
     * @param request
     */
    Boolean isAjaxRequest(HttpServletRequest request);

    /**
     * 内容编码
     *
     * @param str 内容
     * @return 编码后的内容
     */
    String urlEncode(String str);

    /**
     * 内容解码
     *
     * @param str 内容
     * @return 解码后的内容
     */
    String urlDecode(String str);

    /**
     * 设置webflux模型响应
     *
     * @param response ServerHttpResponse
     * @param value    响应内容
     * @return Mono<Void>
     */
    Mono<Void> webFluxResponseWriter(ServerHttpResponse response, Object value);

    /**
     * 设置webflux模型响应
     *
     * @param response ServerHttpResponse
     * @param code     响应状态码
     * @param value    响应内容
     * @return Mono<Void>
     */
    Mono<Void> webFluxResponseWriter(ServerHttpResponse response, Object value, Integer code);

    /**
     * 设置webflux模型响应
     *
     * @param response ServerHttpResponse
     * @param status   http状态码
     * @param code     响应状态码
     * @param value    响应内容
     * @return Mono<Void>
     */
    Mono<Void> webFluxResponseWriter(ServerHttpResponse response, HttpStatus status, Object value, Integer code);

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
    Mono<Void> webFluxResponseWriter(ServerHttpResponse response, String contentType, HttpStatus status, Object value, Integer code);
}
