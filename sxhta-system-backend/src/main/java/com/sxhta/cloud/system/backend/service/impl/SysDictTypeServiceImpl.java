package com.sxhta.cloud.system.backend.service.impl;


import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.extension.toolkit.SqlHelper;
import com.sxhta.cloud.common.constant.UserConstants;
import com.sxhta.cloud.common.exception.ServiceException;
import com.sxhta.cloud.remote.domain.SysDictData;
import com.sxhta.cloud.remote.domain.SysDictType;
import com.sxhta.cloud.security.service.DictionaryService;
import com.sxhta.cloud.system.backend.mapper.SysDictDataMapper;
import com.sxhta.cloud.system.backend.mapper.SysDictTypeMapper;
import com.sxhta.cloud.system.backend.service.ISysDictTypeService;
import jakarta.annotation.PostConstruct;
import jakarta.inject.Inject;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 字典 业务层处理
 */
@Service
public class SysDictTypeServiceImpl implements ISysDictTypeService {
    @Inject
    private SysDictTypeMapper dictTypeMapper;

    @Inject
    private SysDictDataMapper dictDataMapper;

    @Inject
    private DictionaryService dictionaryService;

    /**
     * 项目启动时，初始化字典到缓存
     */
    @PostConstruct
    public void init() {
        loadingDictCache();
    }

    /**
     * 根据条件分页查询字典类型
     *
     * @param dictType 字典类型信息
     * @return 字典类型集合信息
     */
    @Override
    public List<SysDictType> selectDictTypeList(SysDictType dictType) {
        return dictTypeMapper.selectDictTypeList(dictType);
    }

    /**
     * 根据所有字典类型
     *
     * @return 字典类型集合信息
     */
    @Override
    public List<SysDictType> selectDictTypeAll() {
        return dictTypeMapper.selectDictTypeAll();
    }

    /**
     * 根据字典类型查询字典数据
     *
     * @param dictType 字典类型
     * @return 字典数据集合信息
     */
    @Override
    public List<SysDictData> selectDictDataByType(String dictType) {
        var dictDatas = dictionaryService.getDictCache(dictType);
        if (ObjectUtil.isNotEmpty(dictDatas)) {
            return dictDatas;
        }
        dictDatas = dictDataMapper.selectDictDataByType(dictType);
        if (CollUtil.isNotEmpty(dictDatas)) {
            dictionaryService.setDictCache(dictType, dictDatas);
            return dictDatas;
        }
        return null;
    }

    /**
     * 根据字典类型ID查询信息
     *
     * @param dictId 字典类型ID
     * @return 字典类型
     */
    @Override
    public SysDictType selectDictTypeById(Long dictId) {
        return dictTypeMapper.selectDictTypeById(dictId);
    }

    /**
     * 根据字典类型查询信息
     *
     * @param dictType 字典类型
     * @return 字典类型
     */
    @Override
    public SysDictType selectDictTypeByType(String dictType) {
        return dictTypeMapper.selectDictTypeByType(dictType);
    }

    /**
     * 批量删除字典类型信息
     *
     * @param dictIds 需要删除的字典ID
     */
    @Override
    public void deleteDictTypeByIds(Long[] dictIds) {
        for (final var dictId : dictIds) {
            final var dictType = selectDictTypeById(dictId);
            if (dictDataMapper.countDictDataByType(dictType.getDictType()) > 0) {
                throw new ServiceException(String.format("%1$s已分配,不能删除", dictType.getDictName()));
            }
            dictTypeMapper.deleteDictTypeById(dictId);
            dictionaryService.removeDictCache(dictType.getDictType());
        }
    }

    /**
     * 加载字典缓存数据
     */
    @Override
    public void loadingDictCache() {
        final var dictData = new SysDictData();
        dictData.setStatus("0");
        final var dictDataMap = dictDataMapper.selectDictDataList(dictData).stream().collect(Collectors.groupingBy(SysDictData::getDictType));
        for (final var entry : dictDataMap.entrySet()) {
            dictionaryService.setDictCache(entry.getKey(), entry.getValue().stream().sorted(Comparator.comparing(SysDictData::getDictSort)).collect(Collectors.toList()));
        }
    }

    /**
     * 清空字典缓存数据
     */
    @Override
    public void clearDictCache() {
        dictionaryService.clearDictCache();
    }

    /**
     * 重置字典缓存数据
     */
    @Override
    public void resetDictCache() {
        clearDictCache();
        loadingDictCache();
    }

    /**
     * 新增保存字典类型信息
     *
     * @param dict 字典类型信息
     * @return 结果
     */
    @Override
    public Boolean insertDictType(SysDictType dict) {
        final var row = dictTypeMapper.insertDictType(dict);
        if (row > 0) {
            dictionaryService.setDictCache(dict.getDictType(), null);
        }
        return SqlHelper.retBool(row);
    }

    /**
     * 修改保存字典类型信息
     *
     * @param dict 字典类型信息
     * @return 结果
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean updateDictType(SysDictType dict) {
        final var oldDict = dictTypeMapper.selectDictTypeById(dict.getDictId());
        dictDataMapper.updateDictDataType(oldDict.getDictType(), dict.getDictType());
        Integer row = dictTypeMapper.updateDictType(dict);
        if (row > 0) {
            final var dictDatas = dictDataMapper.selectDictDataByType(dict.getDictType());
            dictionaryService.setDictCache(dict.getDictType(), dictDatas);
        }
        return SqlHelper.retBool(row);
    }

    /**
     * 校验字典类型称是否唯一
     *
     * @param dict 字典类型
     * @return 结果
     */
    @Override
    public Boolean checkDictTypeUnique(SysDictType dict) {
        final var dictId = ObjectUtil.isNull(dict.getDictId()) ? -1L : dict.getDictId();
        final var dictType = dictTypeMapper.checkDictTypeUnique(dict.getDictType());
        if (ObjectUtil.isNotNull(dictType) && !dictType.getDictId().equals(dictId)) {
            return UserConstants.NOT_UNIQUE;
        }
        return UserConstants.UNIQUE;
    }
}
