package com.sxhta.cloud.gateway.service;

import java.util.concurrent.TimeUnit;

public interface GatewayRedisService<K, V> {

    void setCacheObject(final K key, final V value, final Long timeout, final TimeUnit timeUnit);


    Boolean expire(final K key, final Long timeout, final TimeUnit unit);


    Boolean hasKey(K key);

    V getCacheObject(final K key);

    Boolean deleteObject(final K key);
}
