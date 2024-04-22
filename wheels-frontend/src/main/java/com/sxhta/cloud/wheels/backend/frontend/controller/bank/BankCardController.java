package com.sxhta.cloud.wheels.frontend.controller.bank;

import com.sxhta.cloud.common.web.domain.CommonResponse;
import com.sxhta.cloud.wheels.frontend.request.bank.BankCardRequest;
import com.sxhta.cloud.wheels.frontend.response.bank.BankCardResponse;
import com.sxhta.cloud.wheels.frontend.service.bank.BankCardService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/bank/card")
@Tag(name = "银行卡管理", description = "银行卡管理控制器")
public class BankCardController {

    @Inject
    private BankCardService bankCardService;

    @Operation(summary = "银行卡列表")
    @GetMapping("/list")
    public CommonResponse<List<BankCardResponse>> getAdminList() {
        final var list = bankCardService.getListByToken();
        return CommonResponse.success(list);
    }

    @Operation(summary = "银行卡详情")
    @GetMapping("/info")
    public CommonResponse<BankCardResponse> getInfoByHash(String hash) {
        final var result = bankCardService.getCardResponse(hash);
        return CommonResponse.success(result);
    }

    @Operation(summary = "创建银行卡")
    @PostMapping("/create")
    public CommonResponse<Boolean> create(@RequestBody BankCardRequest request) {
        final var result = bankCardService.create(request);
        return CommonResponse.result(result);
    }

    @Operation(summary = "银行卡软删除")
    @DeleteMapping("/delete/soft")
    public CommonResponse<Boolean> softDeleteByHash(String hash) {
        final var result = bankCardService.softDelete(hash);
        return CommonResponse.result(result);
    }

    @Operation(summary = "删除银行卡")
    @DeleteMapping("/delete/hard")
    public CommonResponse<Boolean> deleteByHash(String hash) {
        final var result = bankCardService.delete(hash);
        return CommonResponse.result(result);
    }

    @Operation(summary = "更新银行卡")
    @PutMapping(value = "/update")
    public CommonResponse<Boolean> updateEntity(@RequestBody BankCardRequest request) {
        final var result = bankCardService.updateEntity(request);
        return CommonResponse.result(result);
    }
}
