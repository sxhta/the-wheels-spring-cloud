package com.sxhta.cloud.common.context.impl;

import com.alibaba.ttl.TransmittableThreadLocal;
import com.sxhta.cloud.common.constant.SecurityConstants;
import com.sxhta.cloud.common.context.SecurityContextHolder;
import com.sxhta.cloud.common.text.Convert;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 获取当前线程变量中的 用户id、用户名称、Token等信息
 * 注意：必须在网关通过请求头的方法传入，同时在HeaderInterceptor拦截器设置值。 否则这里无法获取
 */
@Component
public class SecurityContextHolderImpl implements SecurityContextHolder {

    private final ThreadLocal<Map<String, Object>> THREAD_LOCAL = new TransmittableThreadLocal<>();

    @Override
    public void set(String key, Object value) {
        final var map = getLocalMap();
        map.put(key, value == null ? StringUtils.EMPTY : value);
    }

    @Override
    public String get(String key) {
        final var map = getLocalMap();
        return Convert.toStr(map.getOrDefault(key, StringUtils.EMPTY));
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T> T get(String key, Class<T> clazz) {
        final var map = getLocalMap();
        return (T) map.getOrDefault(key, null);
    }

    @Override
    public Map<String, Object> getLocalMap() {
        var map = THREAD_LOCAL.get();
        if (map == null) {
            map = new ConcurrentHashMap<>();
            THREAD_LOCAL.set(map);
        }
        return map;
    }

    @Override
    public void setLocalMap(Map<String, Object> threadLocalMap) {
        THREAD_LOCAL.set(threadLocalMap);
    }

    @Override
    public Long getUserId() {
        return Convert.toLong(get(SecurityConstants.DETAILS_USER_ID), 0L);
    }

    @Override
    public void setUserId(String account) {
        set(SecurityConstants.DETAILS_USER_ID, account);
    }

    @Override
    public String getUserName() {
        return get(SecurityConstants.DETAILS_USERNAME);
    }

    @Override
    public void setUserName(String username) {
        set(SecurityConstants.DETAILS_USERNAME, username);
    }

    @Override
    public String getUserKey() {
        return get(SecurityConstants.USER_KEY);
    }

    @Override
    public void setUserKey(String userKey) {
        set(SecurityConstants.USER_KEY, userKey);
    }

    @Override
    public String getPermission() {
        return get(SecurityConstants.ROLE_PERMISSION);
    }

    @Override
    public void setPermission(String permissions) {
        set(SecurityConstants.ROLE_PERMISSION, permissions);
    }

    @Override
    public void remove() {
        THREAD_LOCAL.remove();
    }
}
