package com.sxhta.cloud.security.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.sxhta.cloud.cache.redis.service.RedisService;
import com.sxhta.cloud.common.constant.CacheConstants;
import com.sxhta.cloud.remote.domain.SysDictData;
import com.sxhta.cloud.security.service.DictionaryService;
import jakarta.inject.Inject;
import org.springframework.stereotype.Service;

import java.io.Serial;
import java.io.Serializable;
import java.util.Collection;
import java.util.List;

/**
 * 字典工具类
 */
@Service
public class DictionaryServiceImpl implements DictionaryService, Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Inject
    private RedisService<String, List<SysDictData>> redisService;

    /**
     * 设置字典缓存
     *
     * @param key       参数键
     * @param dictDatas 字典数据列表
     */
    @Override
    public void setDictCache(String key, List<SysDictData> dictDatas) {
        redisService.setCacheObject(getCacheKey(key), dictDatas);
    }

    /**
     * 获取字典缓存
     *
     * @param key 参数键
     * @return dictDatas 字典数据列表
     */
    @Override
    public List<SysDictData> getDictCache(String key) {
        final var cacheArray = redisService.getCacheObject(getCacheKey(key));
        if (ObjectUtil.isNotNull(cacheArray)) {
            return cacheArray;
        }
        return null;
    }

    /**
     * 删除指定字典缓存
     *
     * @param key 字典键
     */
    @Override
    public void removeDictCache(String key) {
        redisService.deleteObject(getCacheKey(key));
    }

    /**
     * 清空字典缓存
     */
    @Override
    public void clearDictCache() {
        final Collection<String> keys = redisService.keys(CacheConstants.SYS_DICT_KEY + "*");
        redisService.deleteObject(keys);
    }

    /**
     * 设置cache key
     *
     * @param configKey 参数键
     * @return 缓存键key
     */
    @Override
    public String getCacheKey(String configKey) {
        return CacheConstants.SYS_DICT_KEY + configKey;
    }
}
