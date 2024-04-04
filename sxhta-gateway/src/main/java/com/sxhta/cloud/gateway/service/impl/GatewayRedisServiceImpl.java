package com.sxhta.cloud.gateway.service.impl;

import com.sxhta.cloud.gateway.service.GatewayRedisService;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.io.Serial;
import java.io.Serializable;
import java.util.concurrent.TimeUnit;

@Singleton
@Service
public class GatewayRedisServiceImpl<K, V> implements GatewayRedisService<K, V>, Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Inject
    public RedisTemplate<K, V> redisTemplate;

    /**
     * 缓存基本的对象，Integer、String、实体类等
     *
     * @param key      缓存的键值
     * @param value    缓存的值
     * @param timeout  时间
     * @param timeUnit 时间颗粒度
     */
    @Override
    public void setCacheObject(final K key, final V value, final Long timeout, final TimeUnit timeUnit) {
        redisTemplate.opsForValue().set(key, value, timeout, timeUnit);
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
}
