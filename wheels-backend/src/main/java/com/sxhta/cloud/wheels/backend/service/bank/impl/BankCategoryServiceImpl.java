package com.sxhta.cloud.wheels.backend.service.bank.impl;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sxhta.cloud.common.exception.CommonNullException;
import com.sxhta.cloud.wheels.backend.mapper.bank.BankCategoryMapper;
import com.sxhta.cloud.wheels.backend.request.bank.BankCategoryRequest;
import com.sxhta.cloud.wheels.backend.request.bank.BankCategorySearchRequest;
import com.sxhta.cloud.wheels.backend.response.bank.BankCategoryResponse;
import com.sxhta.cloud.wheels.backend.service.bank.BankCategoryService;
import com.sxhta.cloud.wheels.remote.domain.bank.BankCategory;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class BankCategoryServiceImpl extends ServiceImpl<BankCategoryMapper, BankCategory>
        implements BankCategoryService {

    @Override
    public Boolean create(BankCategoryRequest request) {
        final var entity = new BankCategory();
        BeanUtils.copyProperties(request, entity);
        return save(entity);
    }

    @Override
    public BankCategoryResponse getInfoByHash(String hash) {
        final var lqw = new LambdaQueryWrapper<BankCategory>();
        lqw.eq(BankCategory::getHash, hash);
        final var entity = getOne(lqw);
        if (ObjectUtil.isNull(entity)) {
            throw new CommonNullException("该分类不存在");
        }
        final var response = new BankCategoryResponse();
        BeanUtils.copyProperties(entity, response);
        return response;
    }

    @Override
    public Boolean softDeleteByHash(String hash) {
        final var lqw = new LambdaQueryWrapper<BankCategory>();
        lqw.eq(BankCategory::getHash, hash);
        final var entity = getOne(lqw);
        if (ObjectUtil.isNull(entity)) {
            throw new CommonNullException("该分类不存在");
        }
        entity.setDeleteTime(LocalDateTime.now());
        return updateById(entity);
    }

    @Override
    public Boolean deleteByHash(String hash) {
        final var lqw = new LambdaQueryWrapper<BankCategory>();
        lqw.eq(BankCategory::getHash, hash);
        final var entity = getOne(lqw);
        if (ObjectUtil.isNull(entity)) {
            throw new CommonNullException("该分类不存在");
        }
        return removeById(entity);
    }

    @Override
    public Boolean updateEntity(BankCategoryRequest request) {
        final var hash = request.getHash();
        final var lqw = new LambdaQueryWrapper<BankCategory>();
        lqw.eq(BankCategory::getHash, hash);
        final var entity = getOne(lqw);
        if (ObjectUtil.isNull(entity)) {
            throw new CommonNullException("该分类不存在");
        }
        BeanUtils.copyProperties(request, entity);
        return updateById(entity);
    }

    @Override
    public List<BankCategoryResponse> getAdminList(BankCategorySearchRequest request) {
        final var lqw = new LambdaQueryWrapper<BankCategory>();
        lqw.isNull(BankCategory::getDeleteTime);
        final var searchName = request.getName();
        if (StrUtil.isNotBlank(searchName)) {
            lqw.and(consumer -> consumer.eq(BankCategory::getName, searchName));
        }
        final var entityList = list(lqw);
        final var responseList = new ArrayList<BankCategoryResponse>();
        entityList.forEach(entity -> {
            final var response = new BankCategoryResponse();
            BeanUtils.copyProperties(entity, response);
            responseList.add(response);
        });
        return responseList;
    }
}
