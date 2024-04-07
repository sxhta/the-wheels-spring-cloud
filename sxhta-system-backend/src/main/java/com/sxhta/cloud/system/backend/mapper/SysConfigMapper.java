package com.sxhta.cloud.system.backend.mapper;

import com.sxhta.cloud.remote.domain.SysConfig;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 参数配置 数据层
 */
@Mapper
public interface SysConfigMapper {
    /**
     * 查询参数配置信息
     *
     * @param config 参数配置信息
     * @return 参数配置信息
     */
    SysConfig selectConfig(SysConfig config);

    /**
     * 通过ID查询配置
     *
     * @param configId 参数ID
     * @return 参数配置信息
     */
    SysConfig selectConfigById(Long configId);

    /**
     * 查询参数配置列表
     *
     * @param config 参数配置信息
     * @return 参数配置集合
     */
    List<SysConfig> selectConfigList(SysConfig config);

    /**
     * 根据键名查询参数配置信息
     *
     * @param configKey 参数键名
     * @return 参数配置信息
     */
    SysConfig checkConfigKeyUnique(String configKey);

    /**
     * 新增参数配置
     *
     * @param config 参数配置信息
     * @return 结果
     */
    Integer insertConfig(SysConfig config);

    /**
     * 修改参数配置
     *
     * @param config 参数配置信息
     * @return 结果
     */
    Integer updateConfig(SysConfig config);

    /**
     * 删除参数配置
     *
     * @param configId 参数ID
     * @return 结果
     */
    Integer deleteConfigById(Long configId);

    /**
     * 批量删除参数信息
     *
     * @param configIds 需要删除的参数ID
     * @return 结果
     */
    Integer deleteConfigByIds(Long[] configIds);
}