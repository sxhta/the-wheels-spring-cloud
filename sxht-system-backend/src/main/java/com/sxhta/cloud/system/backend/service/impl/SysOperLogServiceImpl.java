package com.sxhta.cloud.system.backend.service.impl;

import com.baomidou.mybatisplus.extension.toolkit.SqlHelper;
import com.sxhta.cloud.remote.domain.SysOperLog;
import com.sxhta.cloud.system.backend.mapper.SysOperLogMapper;
import com.sxhta.cloud.system.backend.service.ISysOperLogService;
import jakarta.inject.Inject;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 操作日志 服务层处理
 */
@Service
public class SysOperLogServiceImpl implements ISysOperLogService {

    @Inject
    private SysOperLogMapper operLogMapper;

    /**
     * 新增操作日志
     *
     * @param operLog 操作日志对象
     * @return 结果
     */
    @Override
    public Boolean insertOperlog(SysOperLog operLog) {
        return SqlHelper.retBool(operLogMapper.insertOperlog(operLog));
    }

    /**
     * 查询系统操作日志集合
     *
     * @param operLog 操作日志对象
     * @return 操作日志集合
     */
    @Override
    public List<SysOperLog> selectOperLogList(SysOperLog operLog) {
        return operLogMapper.selectOperLogList(operLog);
    }

    /**
     * 批量删除系统操作日志
     *
     * @param operIds 需要删除的操作日志ID
     * @return 结果
     */
    @Override
    public Boolean deleteOperLogByIds(Long[] operIds) {
        return SqlHelper.retBool(operLogMapper.deleteOperLogByIds(operIds));
    }

    /**
     * 查询操作日志详细
     *
     * @param operId 操作ID
     * @return 操作日志对象
     */
    @Override
    public SysOperLog selectOperLogById(Long operId) {
        return operLogMapper.selectOperLogById(operId);
    }

    /**
     * 清空操作日志
     */
    @Override
    public void cleanOperLog() {
        operLogMapper.cleanOperLog();
    }
}
