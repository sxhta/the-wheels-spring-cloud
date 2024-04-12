package com.sxhta.cloud.wheels.controller.fee;

import com.sxhta.cloud.common.web.controller.BaseController;
import com.sxhta.cloud.common.web.controller.ICommonController;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.web.bind.annotation.*;
import jakarta.inject.Inject;
import io.swagger.v3.oas.annotations.tags.Tag;
import com.sxhta.cloud.common.web.domain.CommonResponse;
import com.sxhta.cloud.common.web.page.TableDataInfo;

import java.io.Serializable;
import java.io.Serial;
import com.sxhta.cloud.common.web.page.PageRequest;
import com.sxhta.cloud.wheels.request.OtherFeeRequest;
import com.sxhta.cloud.wheels.response.OtherFeeResponse;
import com.sxhta.cloud.wheels.request.OtherFeeSearchRequest;
import com.sxhta.cloud.wheels.service.fee.OtherFeeService;

import org.springframework.web.bind.annotation.RestController;

/**
 * 其他费用配置表 前端控制器
 */
@Tag(name = "其他费用配置表", description = "其他费用配置表"+"控制器")
@RestController
@RequestMapping("/other/fee")
public class OtherFeeController extends BaseController implements ICommonController<OtherFeeSearchRequest,OtherFeeRequest,OtherFeeResponse> ,Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Inject
    private OtherFeeService otherFeeService;

    @Override
    @PostMapping("/create")
    @Operation(summary = "新增")
    public CommonResponse<Boolean> create(@RequestBody OtherFeeRequest request) {
        return CommonResponse.result(otherFeeService.create(request));
    }

    @Override
    @DeleteMapping("/delete")
    @Operation(summary = "删除")
    public CommonResponse<Boolean> deleteByHash(@RequestParam(value = "hash") String hash) {
        return CommonResponse.result(otherFeeService.deleteByHash(hash));
    }

    @Override
    @Operation(summary = "修改")
    @PutMapping("/update")
    public CommonResponse<Boolean> updateEntity(@RequestBody OtherFeeRequest otherFeeRequest) {
        return null;
    }

    @Override
    @GetMapping("/info")
    @Operation(summary = "详情")
    public CommonResponse<OtherFeeResponse> getInfoByHash(@RequestParam("hash") String hash) {
        return CommonResponse.success(otherFeeService.getInfoByHash(hash));
    }

    @Override
    @GetMapping("/list")
    @Operation(summary = "列表")
    public TableDataInfo<OtherFeeResponse> getAdminList(@ModelAttribute("OtherFeeSearchRequest") OtherFeeSearchRequest request,PageRequest pageRequest) {
        startPage(pageRequest);
        return CommonResponse.list(otherFeeService.getAdminList(request));
    }

    @Override
    @DeleteMapping("/soft")
    @Operation(summary = "软删除")
    public CommonResponse<Boolean> softDeleteByHash(String hash) {
        return null;
    }
}
