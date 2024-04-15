package com.sxhta.cloud.wheels.backend.service.fee.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.sxhta.cloud.common.exception.CommonNullException;
import com.sxhta.cloud.remote.domain.SysUser;
import com.sxhta.cloud.remote.vo.SystemUserCacheVo;
import com.sxhta.cloud.wheels.backend.entity.fee.OtherFee;
import com.sxhta.cloud.wheels.backend.mapper.fee.OtherFeeMapper;
import com.sxhta.cloud.wheels.backend.request.OtherFeeRequest;
import com.sxhta.cloud.wheels.backend.request.OtherFeeSearchRequest;
import com.sxhta.cloud.wheels.backend.response.OtherFeeResponse;
import com.sxhta.cloud.wheels.backend.service.fee.OtherFeeService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import jakarta.inject.Inject;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.sxhta.cloud.security.service.TokenService;

/**
 * 其他费用配置表 服务实现类
 */
@Service
public class OtherFeeServiceImpl extends ServiceImpl<OtherFeeMapper, OtherFee> implements OtherFeeService, Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Inject
    private TokenService<SystemUserCacheVo, SysUser> tokenService;


    @Override
    public Boolean create(OtherFeeRequest request) {
        final var res = this.getByName(request.getName());
        if (ObjectUtil.isNotNull(res)) {
            throw new CommonNullException("该名称已存在！");
        }
        final var otherFee = new OtherFee();
        BeanUtils.copyProperties(request,otherFee);
        otherFee.setCreateBy(String.valueOf(tokenService.getUserId()));
        return save(otherFee);
    }

    @Override
    public Boolean deleteByHash(String hash) {
        final var otherFee = this.getByHash(hash);
        if (ObjectUtil.isNull(otherFee)) {
            throw new CommonNullException("该条数据不存在！");
        }
        return removeById(otherFee);
    }

    @Override
    public Boolean updateEntity(OtherFeeRequest request) {
        final var otherFee = this.getByHash(request.getHash());
        if (ObjectUtil.isNull(otherFee)) {
            throw new CommonNullException("该条数据不存在！");
        }
        final var lqw = new LambdaQueryWrapper<OtherFee>();
        lqw.eq(OtherFee::getName,request.getName())
           .ne(OtherFee::getName,otherFee.getName());
        final var res = getOne(lqw);
        if (ObjectUtil.isNotNull(res)) {
            throw new CommonNullException("该名称已存在！");
        }
        otherFee.setName(request.getName());
        otherFee.setRemark(request.getRemark());
        otherFee.setUpdateTime(LocalDateTime.now());
        otherFee.setCreateBy(String.valueOf(tokenService.getUserId()));
        return updateById(otherFee);
    }

    @Override
    public OtherFeeResponse getInfoByHash(String hash) {
        final var otherFee = this.getByHash(hash);
        if (ObjectUtil.isNull(otherFee)) {
            throw new CommonNullException("该条数据不存在！");
        }
        final var response = new OtherFeeResponse();
        BeanUtils.copyProperties(otherFee,response);
        return response;
    }


    @Override
    public List<OtherFeeResponse> getAdminList(OtherFeeSearchRequest request) {
        final var lqw = new LambdaQueryWrapper<OtherFee>();
        lqw.isNull(OtherFee::getDeleteTime);
        if (StrUtil.isNotBlank(request.getName())) {
            lqw.and(i->i.like(OtherFee::getName,request.getName()));
        }
        lqw.orderByDesc(OtherFee::getCreateTime);
        final var responseList= new ArrayList<OtherFeeResponse>();
        final var otherFeeList = list(lqw);
        if (CollUtil.isEmpty(otherFeeList)) {
            return responseList;
        }
        return otherFeeList.stream().map(otherFee -> {
            final var response = new OtherFeeResponse();
            BeanUtils.copyProperties(otherFee,response);
            return response;
        }).collect(Collectors.toList());
    }

    private OtherFee getByHash(String hash) {
        final var lqw = new LambdaQueryWrapper<OtherFee>();
        lqw.eq(OtherFee::getHash, hash);
        return getOne(lqw);
    }

    private OtherFee getByName(String name){
        final var lqw = new LambdaQueryWrapper<OtherFee>();
        lqw.eq(OtherFee::getName,name);
        return getOne(lqw);
    }

    @Override
    public Boolean softDeleteByHash(String hash) {
        return null;
    }

}
