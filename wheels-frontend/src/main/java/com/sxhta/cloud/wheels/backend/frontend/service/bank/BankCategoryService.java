package com.sxhta.cloud.wheels.frontend.service.bank;

import com.baomidou.mybatisplus.extension.service.IService;
import com.sxhta.cloud.wheels.frontend.response.bank.BankCategoryResponse;
import com.sxhta.cloud.wheels.remote.domain.bank.BankCategory;

import java.util.List;

public interface BankCategoryService extends IService<BankCategory> {

    List<BankCategoryResponse> getList();
}
