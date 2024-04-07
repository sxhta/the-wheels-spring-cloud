package com.sxhta.cloud.system.backend.service.impl;

import com.baomidou.mybatisplus.extension.toolkit.SqlHelper;
import com.sxhta.cloud.remote.domain.SysLogininfor;
import com.sxhta.cloud.system.backend.mapper.SysLoginInfoMapper;
import com.sxhta.cloud.system.backend.service.ISysLoginInfoService;
import jakarta.inject.Inject;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 系统访问日志情况信息 服务层处理
 */
@Service
public class SysLoginInfoServiceImpl implements ISysLoginInfoService {

    @Inject
    private SysLoginInfoMapper loginInfoMapper;

    /**
     * 新增系统登录日志
     *
     * @param logininfor 访问日志对象
     */
    @Override
    public Boolean insertLogininfor(SysLogininfor logininfor) {
        return SqlHelper.retBool(loginInfoMapper.insertLogininfor(logininfor));
    }

    /**
     * 查询系统登录日志集合
     *
     * @param logininfor 访问日志对象
     * @return 登录记录集合
     */
    @Override
    public List<SysLogininfor> selectLogininforList(SysLogininfor logininfor) {
        return loginInfoMapper.selectLogininforList(logininfor);
    }

    /**
     * 批量删除系统登录日志
     *
     * @param infoIds 需要删除的登录日志ID
     * @return 结果
     */
    @Override
    public Boolean deleteLogininforByIds(Long[] infoIds) {
        return SqlHelper.retBool(loginInfoMapper.deleteLogininforByIds(infoIds));
    }

    /**
     * 清空系统登录日志
     */
    @Override
    public void cleanLogininfor() {
        loginInfoMapper.cleanLogininfor();
    }
}
