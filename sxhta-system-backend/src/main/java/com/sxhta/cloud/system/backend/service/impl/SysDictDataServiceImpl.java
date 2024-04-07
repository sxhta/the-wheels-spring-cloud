package com.sxhta.cloud.system.backend.service.impl;


import com.baomidou.mybatisplus.extension.toolkit.SqlHelper;
import com.sxhta.cloud.remote.domain.SysDictData;
import com.sxhta.cloud.security.service.DictionaryService;
import com.sxhta.cloud.system.backend.mapper.SysDictDataMapper;
import com.sxhta.cloud.system.backend.service.ISysDictDataService;
import jakarta.inject.Inject;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 字典 业务层处理
 */
@Service
public class SysDictDataServiceImpl implements ISysDictDataService {

    @Inject
    private SysDictDataMapper dictDataMapper;

    @Inject
    private DictionaryService dictionaryService;

    /**
     * 根据条件分页查询字典数据
     *
     * @param dictData 字典数据信息
     * @return 字典数据集合信息
     */
    @Override
    public List<SysDictData> selectDictDataList(SysDictData dictData) {
        return dictDataMapper.selectDictDataList(dictData);
    }

    /**
     * 根据字典类型和字典键值查询字典数据信息
     *
     * @param dictType  字典类型
     * @param dictValue 字典键值
     * @return 字典标签
     */
    @Override
    public String selectDictLabel(String dictType, String dictValue) {
        return dictDataMapper.selectDictLabel(dictType, dictValue);
    }

    /**
     * 根据字典数据ID查询信息
     *
     * @param dictCode 字典数据ID
     * @return 字典数据
     */
    @Override
    public SysDictData selectDictDataById(Long dictCode) {
        return dictDataMapper.selectDictDataById(dictCode);
    }

    /**
     * 批量删除字典数据信息
     *
     * @param dictCodes 需要删除的字典数据ID
     */
    @Override
    public void deleteDictDataByIds(Long[] dictCodes) {
        for (final var dictCode : dictCodes) {
            final var data = selectDictDataById(dictCode);
            dictDataMapper.deleteDictDataById(dictCode);
            final var dictDatas = dictDataMapper.selectDictDataByType(data.getDictType());
            dictionaryService.setDictCache(data.getDictType(), dictDatas);
        }
    }

    /**
     * 新增保存字典数据信息
     *
     * @param data 字典数据信息
     * @return 结果
     */
    @Override
    public Boolean insertDictData(SysDictData data) {
        final var row = dictDataMapper.insertDictData(data);
        if (row > 0) {
            final var dictDatas = dictDataMapper.selectDictDataByType(data.getDictType());
            dictionaryService.setDictCache(data.getDictType(), dictDatas);
        }
        return SqlHelper.retBool(row);
    }

    /**
     * 修改保存字典数据信息
     *
     * @param data 字典数据信息
     * @return 结果
     */
    @Override
    public Boolean updateDictData(SysDictData data) {
        final var row = dictDataMapper.updateDictData(data);
        if (row > 0) {
            final var dictDatas = dictDataMapper.selectDictDataByType(data.getDictType());
            dictionaryService.setDictCache(data.getDictType(), dictDatas);
        }
        return SqlHelper.retBool(row);
    }
}
