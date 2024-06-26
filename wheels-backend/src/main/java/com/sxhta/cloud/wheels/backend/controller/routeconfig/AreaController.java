package com.sxhta.cloud.wheels.backend.controller.routeconfig;

import com.sxhta.cloud.common.web.controller.BaseController;
import com.sxhta.cloud.common.web.controller.ICommonController;
import com.sxhta.cloud.common.web.domain.CommonResponse;
import com.sxhta.cloud.common.web.page.PageRequest;
import com.sxhta.cloud.common.web.page.TableDataInfo;
import com.sxhta.cloud.wheels.backend.request.AreaRequest;
import com.sxhta.cloud.wheels.backend.request.AreaSearchRequest;
import com.sxhta.cloud.wheels.backend.response.AreaResponse;
import com.sxhta.cloud.wheels.backend.service.routeconfig.AreaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.inject.Inject;
import org.springframework.web.bind.annotation.*;

import java.io.Serial;
import java.io.Serializable;

/**
 * 地区配置表 前端控制器
 */
@Tag(name = "地区配置表", description = "地区配置表" + "控制器")
@RestController
@RequestMapping("/area")
public class AreaController extends BaseController implements ICommonController<AreaSearchRequest, AreaRequest, AreaResponse>, Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Inject
    private AreaService areaService;

    @Override
    @Operation(summary = "新增")
    @PostMapping("/create")
    public CommonResponse<Boolean> create(@RequestBody AreaRequest request) {
        return CommonResponse.result(areaService.create(request));
    }

    @Override
    @Operation(summary = "删除")
    @DeleteMapping("/delete")
    public CommonResponse<Boolean> deleteByHash(@RequestParam(value = "hash") String hash) {
        return CommonResponse.result(areaService.deleteByHash(hash));
    }

    @Override
    @Operation(summary = "修改")
    @PutMapping("/update")
    public CommonResponse<Boolean> updateEntity(@RequestBody AreaRequest areaRequest) {
        return CommonResponse.result(areaService.updateEntity(areaRequest));
    }

    @Override
    @Operation(summary = "软删除")
    @DeleteMapping("/soft")
    public CommonResponse<Boolean> softDeleteByHash(String hash) {
        return null;
    }


    @Override
    @Operation(summary = "详情")
    @GetMapping("/info")
    public CommonResponse<AreaResponse> getInfoByHash(@RequestParam("hash") String hash) {
        return CommonResponse.success(areaService.getInfoByHash(hash));
    }

    @Override
    @Operation(summary = "列表")
    @GetMapping("/list")
    public TableDataInfo<AreaResponse> getAdminList(@ModelAttribute("AreaSearchRequest") AreaSearchRequest request, PageRequest pageRequest) {
        startPage(pageRequest);
        return CommonResponse.list(areaService.getAdminList(request));
    }
}
