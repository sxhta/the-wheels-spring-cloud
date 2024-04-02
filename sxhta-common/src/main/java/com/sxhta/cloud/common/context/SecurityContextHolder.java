package com.sxhta.cloud.common.context;

import java.util.Map;

public interface SecurityContextHolder {

    void set(String key, Object value);

    String get(String key) ;

    <T> T get(String key, Class<T> clazz);

    Map<String, Object> getLocalMap() ;

    void setLocalMap(Map<String, Object> threadLocalMap);

    Long getUserId();

    void setUserId(String account);

    String getUserName() ;
    void setUserName(String username);

    String getUserKey();

    void setUserKey(String userKey);

    String getPermission();

    void setPermission(String permissions);

    void remove();
}
