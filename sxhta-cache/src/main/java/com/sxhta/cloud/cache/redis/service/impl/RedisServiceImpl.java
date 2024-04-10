package com.sxhta.cloud.cache.redis.service.impl;

import com.sxhta.cloud.cache.redis.service.RedisService;
import com.sxhta.cloud.common.exception.CommonException;
import jakarta.annotation.PostConstruct;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import org.springframework.data.redis.core.BoundSetOperations;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.service.annotation.PostExchange;

import java.io.Serial;
import java.io.Serializable;
import java.util.*;
import java.util.concurrent.TimeUnit;

@Singleton
@Service
public class RedisServiceImpl<K, V> implements RedisService<K, V>, Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Inject
    public RedisTemplate<K, V> redisTemplate;

    /**
     * 缓存基本的对象，Integer、String、实体类等
     *
     * @param key   缓存的键值
     * @param value 缓存的值
     */
    @Override
    public void setCacheObject(final K key, final V value) {
        redisTemplate.opsForValue().set(key, value);
    }

    /**
     * 缓存基本的对象，Integer、String、实体类等
     *
     * @param key      缓存的键值
     * @param value    缓存的值
     * @param timeout  时间
     * @param timeUnit 时间颗粒度
     */
    @SuppressWarnings("CallToPrintStackTrace")
    @Override
    public Boolean setCacheObject(final K key, final V value, final Long timeout, final TimeUnit timeUnit) {
        try {
            redisTemplate.opsForValue().set(key, value, timeout, timeUnit);
            return true;
        } catch (CommonException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 设置有效时间
     *
     * @param key     Redis键
     * @param timeout 超时时间
     * @return true=设置成功；false=设置失败
     */
    @Override
    public Boolean expire(final K key, final Long timeout) {
        return expire(key, timeout, TimeUnit.SECONDS);
    }

    /**
     * 设置有效时间
     *
     * @param key     Redis键
     * @param timeout 超时时间
     * @param unit    时间单位
     * @return true=设置成功；false=设置失败
     */
    @Override
    public Boolean expire(final K key, final Long timeout, final TimeUnit unit) {
        return redisTemplate.expire(key, timeout, unit);
    }

    /**
     * 获取有效时间
     *
     * @param key Redis键
     * @return 有效时间
     */
    @Override
    public Long getExpire(final K key) {
        return redisTemplate.getExpire(key);
    }

    /**
     * 判断 key是否存在
     *
     * @param key 键
     * @return true 存在 false不存在
     */
    @Override
    public Boolean hasKey(K key) {
        return redisTemplate.hasKey(key);
    }

    /**
     * 获得缓存的基本对象。
     *
     * @param key 缓存键值
     * @return 缓存键值对应的数据
     */
    @Override
    public V getCacheObject(final K key) {
        final var operation = redisTemplate.opsForValue();
        return operation.get(key);
    }

    /**
     * 删除单个对象
     */
    @Override
    public Boolean deleteObject(final K key) {
        return redisTemplate.delete(key);
    }

    /**
     * 删除集合对象
     *
     * @param collection 多个对象
     */
    @Override
    public Boolean deleteObject(final Collection<K> collection) {
        return Objects.requireNonNull(redisTemplate.delete(collection)).compareTo(0L) > 0;
    }

    /**
     * 缓存List数据
     *
     * @param key      缓存的键值
     * @param dataList 待缓存的List数据
     * @return 缓存的对象
     */
    @Override
    public Long setCacheList(final K key, final List<V> dataList) {
        final var count = redisTemplate.opsForList().rightPushAll(key, dataList);
        return count == null ? 0 : count;
    }

    /**
     * 获得缓存的list对象
     *
     * @param key 缓存的键值
     * @return 缓存键值对应的数据
     */
    @Override
    public List<V> getCacheList(final K key) {
        return redisTemplate.opsForList().range(key, 0, -1);
    }

    /**
     * 缓存Set
     *
     * @param key     缓存键值
     * @param dataSet 缓存的数据
     * @return 缓存数据的对象
     */
    @SuppressWarnings("unchecked")
    @Override
    public BoundSetOperations<K, V> setCacheSet(final K key, final Set<V> dataSet) {
        final var setOperation = redisTemplate.boundSetOps(key);
        for (final var value : dataSet) {
            setOperation.add(value);
        }
        return setOperation;
    }

    /**
     * 获得缓存的set
     */
    @Override
    public Set<V> getCacheSet(final K key) {
        return redisTemplate.opsForSet().members(key);
    }

    /**
     * 缓存Map
     */
    @Override
    public void setCacheMap(final K key, final Map<String, ?> dataMap) {
        if (dataMap != null) {
            redisTemplate.opsForHash().putAll(key, dataMap);
        }
    }

    /**
     * 获得缓存的Map
     */
    @Override
    public Map<?, ?> getCacheMap(final K key) {
        return redisTemplate.opsForHash().entries(key);
    }

    /**
     * 往Hash中存入数据
     *
     * @param key   Redis键
     * @param hKey  Hash键
     * @param value 值
     */
    @Override
    public <MapV> void setCacheMapValue(final K key, final String hKey, final MapV value) {
        redisTemplate.opsForHash().put(key, hKey, value);
    }

    /**
     * 获取Hash中的数据
     *
     * @param key  Redis键
     * @param hKey Hash键
     * @return Hash中的对象
     */
    @Override
    public <MapV> MapV getCacheMapValue(final K key, final String hKey) {
        final HashOperations<K, String, MapV> opsForHash = redisTemplate.opsForHash();
        return opsForHash.get(key, hKey);
    }

    /**
     * 获取多个Hash中的数据
     *
     * @param key   Redis键
     * @param hKeys Hash键集合
     * @return Hash对象集合
     */
    @Override
    public List<Object> getMultiCacheMapValue(final K key, final Collection<Object> hKeys) {
        return redisTemplate.opsForHash().multiGet(key, hKeys);
    }

    /**
     * 删除Hash中的某条数据
     *
     * @param key  Redis键
     * @param hKey Hash键
     * @return 是否成功
     */
    @Override
    public Boolean deleteCacheMapValue(final K key, final String hKey) {
        return redisTemplate.opsForHash().delete(key, hKey) > 0;
    }

    /**
     * 获得缓存的基本对象列表
     *
     * @param pattern 字符串前缀
     * @return 对象列表
     */
    @Override
    public Collection<K> keys(final K pattern) {
        return redisTemplate.keys(pattern);
    }
}
