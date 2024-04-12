package com.wheels.cloud.frontend.service.routeconfig.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sxhta.cloud.remote.domain.SysUser;
import com.sxhta.cloud.remote.vo.SystemUserCacheVo;
import com.sxhta.cloud.security.service.TokenService;
import com.wheels.cloud.frontend.entity.routeconfig.OtherFee;
import com.wheels.cloud.frontend.mapper.routeconfig.OtherFeeMapper;
import com.wheels.cloud.frontend.service.routeconfig.OtherFeeService;
import jakarta.inject.Inject;
import org.springframework.stereotype.Service;

import java.io.Serial;
import java.io.Serializable;

/**
 * 其他费用配置表 服务实现类
 */
@Service
public class OtherFeeServiceImpl extends ServiceImpl<OtherFeeMapper, OtherFee> implements OtherFeeService, Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Inject
    private TokenService<SystemUserCacheVo, SysUser> tokenService;




}
