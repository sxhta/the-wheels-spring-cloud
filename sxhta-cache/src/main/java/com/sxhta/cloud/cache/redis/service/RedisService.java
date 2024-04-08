package com.sxhta.cloud.cache.redis.service;

import org.springframework.data.redis.core.BoundSetOperations;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

public interface RedisService<K, V> {

    void setCacheObject(final K key, final V value);

    Boolean setCacheObject(final K key, final V value, final Long timeout, final TimeUnit timeUnit);

    Boolean expire(final K key, final Long timeout);

    Boolean expire(final K key, final Long timeout, final TimeUnit unit);

    Long getExpire(final K key);

    Boolean hasKey(K key);

    V getCacheObject(final K key);

    Boolean deleteObject(final K key);

    Boolean deleteObject(final Collection<K> collection);

    Long setCacheList(final K key, final List<V> dataList);

    List<V> getCacheList(final K key);

    BoundSetOperations<K, V> setCacheSet(final K key, final Set<V> dataSet);

    Set<V> getCacheSet(final K key);

    void setCacheMap(final K key, final Map<String, ?> dataMap);

    Map<?, ?> getCacheMap(final K key);

    <MapV> void setCacheMapValue(final K key, final String hKey, final MapV value);

    <MapV> MapV getCacheMapValue(final K key, final String hKey);

    List<Object> getMultiCacheMapValue(final K key, final Collection<Object> hKeys);

    Boolean deleteCacheMapValue(final K key, final String hKey);

    Collection<K> keys(final K pattern);
}
