package com.sxhta.cloud.wheels.backend.service.bank;

import com.baomidou.mybatisplus.extension.service.IService;
import com.sxhta.cloud.common.service.ICommonService;
import com.sxhta.cloud.wheels.backend.request.bank.BankCategoryRequest;
import com.sxhta.cloud.wheels.backend.request.bank.BankCategorySearchRequest;
import com.sxhta.cloud.wheels.backend.response.bank.BankCategoryResponse;
import com.sxhta.cloud.wheels.remote.domain.bank.BankCategory;

public interface BankCategoryService extends IService<BankCategory>,
        ICommonService<BankCategorySearchRequest, BankCategoryRequest, BankCategoryResponse> {
}
