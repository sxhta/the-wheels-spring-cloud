package com.sxhta.cloud.wheels.backend.controller.bank;

import com.sxhta.cloud.common.web.controller.BaseController;
import com.sxhta.cloud.common.web.controller.ICommonController;
import com.sxhta.cloud.common.web.domain.CommonResponse;
import com.sxhta.cloud.common.web.page.PageRequest;
import com.sxhta.cloud.common.web.page.TableDataInfo;
import com.sxhta.cloud.wheels.backend.request.bank.BankCategoryRequest;
import com.sxhta.cloud.wheels.backend.request.bank.BankCategorySearchRequest;
import com.sxhta.cloud.wheels.backend.response.bank.BankCategoryResponse;
import com.sxhta.cloud.wheels.backend.service.bank.BankCategoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/bank/category")
@Tag(name = "银行卡类型管理", description = "银行卡类型管理控制器")
public class BankCategoryController extends BaseController implements
        ICommonController<BankCategorySearchRequest, BankCategoryRequest, BankCategoryResponse> {

    @Inject
    private BankCategoryService bankCategoryService;

    @Override
    @Operation(summary = "银行卡类型列表")
    @GetMapping("/list")
    public TableDataInfo<BankCategoryResponse> getAdminList(BankCategorySearchRequest request, PageRequest pageRequest) {
        startPage(pageRequest);
        final var list = bankCategoryService.getAdminList(request);
        return CommonResponse.list(list);
    }

    @Override
    @Operation(summary = "银行卡类型详情")
    @GetMapping("/info")
    public CommonResponse<BankCategoryResponse> getInfoByHash(String hash) {
        final var result = bankCategoryService.getInfoByHash(hash);
        return CommonResponse.success(result);
    }

    @Override
    @Operation(summary = "创建银行卡类型")
    @PostMapping("/create")
    public CommonResponse<Boolean> create(@RequestBody BankCategoryRequest request) {
        final var result = bankCategoryService.create(request);
        return CommonResponse.result(result);
    }

    @Override
    @Operation(summary = "银行卡类型软删除")
    @DeleteMapping("/delete/soft")
    public CommonResponse<Boolean> softDeleteByHash(String hash) {
        final var result = bankCategoryService.softDeleteByHash(hash);
        return CommonResponse.result(result);
    }

    @Override
    @Operation(summary = "删除银行卡类型")
    @DeleteMapping("/delete/hard")
    public CommonResponse<Boolean> deleteByHash(String hash) {
        final var result = bankCategoryService.deleteByHash(hash);
        return CommonResponse.result(result);
    }

    @Override
    @Operation(summary = "更新银行卡类型")
    @PutMapping(value = "/update")
    public CommonResponse<Boolean> updateEntity(@RequestBody BankCategoryRequest request) {
        final var result = bankCategoryService.updateEntity(request);
        return CommonResponse.result(result);
    }
}
