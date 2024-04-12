package com.wheels.cloud.frontend.service.bank;

import com.baomidou.mybatisplus.extension.service.IService;
import com.sxhta.cloud.wheels.remote.domain.bank.BankCategory;
import com.wheels.cloud.frontend.response.bank.BankCategoryResponse;

import java.util.List;

public interface BankCategoryService extends IService<BankCategory> {

    List<BankCategoryResponse> getList();
}
