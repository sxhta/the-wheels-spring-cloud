package com.wheels.cloud.frontend.controller.bank;

import com.sxhta.cloud.common.web.domain.CommonResponse;
import com.wheels.cloud.frontend.response.bank.BankCategoryResponse;
import com.wheels.cloud.frontend.service.bank.BankCategoryService;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/bank/category")
public class BankCategoryController implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Inject
    private BankCategoryService bankCategoryService;

    @GetMapping("/category")
    public CommonResponse<List<BankCategoryResponse>> getUserInfo() {
        final var info = bankCategoryService.getList();
        return CommonResponse.success(info);
    }
}