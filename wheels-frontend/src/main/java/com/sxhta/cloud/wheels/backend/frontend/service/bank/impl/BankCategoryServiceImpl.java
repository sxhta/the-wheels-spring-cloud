package com.sxhta.cloud.wheels.frontend.service.bank.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sxhta.cloud.wheels.frontend.mapper.bank.BankCategoryMapper;
import com.sxhta.cloud.wheels.frontend.response.bank.BankCategoryResponse;
import com.sxhta.cloud.wheels.frontend.service.bank.BankCategoryService;
import com.sxhta.cloud.wheels.remote.domain.bank.BankCategory;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class BankCategoryServiceImpl extends ServiceImpl<BankCategoryMapper, BankCategory>
        implements BankCategoryService {

    @Override
    public List<BankCategoryResponse> getList() {
        final var lqw = new LambdaQueryWrapper<BankCategory>();
        lqw.eq(BankCategory::getStatus, 1)
                .orderByDesc(BankCategory::getId);
        final var list = list(lqw);
        final var responseList = new ArrayList<BankCategoryResponse>();
        list.forEach(item -> {
            final var response = new BankCategoryResponse();
            BeanUtils.copyProperties(item, response);
            responseList.add(response);
        });
        return responseList;
    }
}
