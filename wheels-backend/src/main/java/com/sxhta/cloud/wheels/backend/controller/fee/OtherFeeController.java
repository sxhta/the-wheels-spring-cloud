package com.sxhta.cloud.wheels.backend.controller.fee;

import com.sxhta.cloud.common.web.controller.BaseController;
import com.sxhta.cloud.common.web.controller.ICommonController;
import com.sxhta.cloud.common.web.domain.CommonResponse;
import com.sxhta.cloud.common.web.page.PageRequest;
import com.sxhta.cloud.common.web.page.TableDataInfo;
import com.sxhta.cloud.wheels.backend.request.OtherFeeRequest;
import com.sxhta.cloud.wheels.backend.request.OtherFeeSearchRequest;
import com.sxhta.cloud.wheels.backend.response.OtherFeeResponse;
import com.sxhta.cloud.wheels.backend.service.fee.OtherFeeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.inject.Inject;
import org.springframework.web.bind.annotation.*;

import java.io.Serial;
import java.io.Serializable;

/**
 * 其他费用配置表 前端控制器
 */
@Tag(name = "其他费用配置表", description = "其他费用配置表" + "控制器")
@RestController
@RequestMapping("/other/fee")
public class OtherFeeController extends BaseController implements ICommonController<OtherFeeSearchRequest, OtherFeeRequest, OtherFeeResponse>, Serializable {

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
        return CommonResponse.result(otherFeeService.updateEntity(otherFeeRequest));
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
    public TableDataInfo<OtherFeeResponse> getAdminList(@ModelAttribute("OtherFeeSearchRequest") OtherFeeSearchRequest request, PageRequest pageRequest) {
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
