package com.sxhta.cloud.wheels.service.bank;

import com.baomidou.mybatisplus.extension.service.IService;
import com.sxhta.cloud.common.service.ICommonService;
import com.sxhta.cloud.wheels.remote.domain.bank.BankCategory;
import com.sxhta.cloud.wheels.request.bank.BankCategoryRequest;
import com.sxhta.cloud.wheels.request.bank.BankCategorySearchRequest;
import com.sxhta.cloud.wheels.response.bank.BankCategoryResponse;

public interface BankCategoryService extends IService<BankCategory>,
        ICommonService<BankCategorySearchRequest, BankCategoryRequest, BankCategoryResponse> {
}
