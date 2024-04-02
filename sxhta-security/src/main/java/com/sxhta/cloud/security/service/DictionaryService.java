package com.sxhta.cloud.security.service;

import com.sxhta.cloud.remote.domain.SysDictData;

import java.util.List;

public interface DictionaryService {

    /**
     * 设置字典缓存
     *
     * @param key       参数键
     * @param dictDatas 字典数据列表
     */
    void setDictCache(String key, List<SysDictData> dictDatas);

    /**
     * 获取字典缓存
     *
     * @param key 参数键
     * @return dictDatas 字典数据列表
     */
    List<SysDictData> getDictCache(String key);

    /**
     * 删除指定字典缓存
     *
     * @param key 字典键
     */
    void removeDictCache(String key);

    /**
     * 清空字典缓存
     */
    void clearDictCache();

    /**
     * 设置cache key
     *
     * @param configKey 参数键
     * @return 缓存键key
     */
    String getCacheKey(String configKey);
}
