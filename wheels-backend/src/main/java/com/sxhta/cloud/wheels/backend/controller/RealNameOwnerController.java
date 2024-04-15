package com.sxhta.cloud.wheels.backend.controller;

import com.sxhta.cloud.common.web.controller.BaseController;
import com.sxhta.cloud.common.web.controller.ICommonController;
import com.sxhta.cloud.common.web.domain.CommonResponse;
import com.sxhta.cloud.common.web.page.PageRequest;
import com.sxhta.cloud.common.web.page.TableDataInfo;
import com.sxhta.cloud.wheels.backend.request.RealNameOwnerRequest;
import com.sxhta.cloud.wheels.backend.request.RealNameOwnerSearchRequest;
import com.sxhta.cloud.wheels.backend.response.RealNameOwnerResponse;
import com.sxhta.cloud.wheels.backend.service.RealNameOwnerService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.inject.Inject;
import org.springframework.web.bind.annotation.*;

import java.io.Serializable;

@RestController
@RequestMapping("/real/name/owner")
@Tag(name = "实名认证", description = "实名认证管理器")
public class RealNameOwnerController extends BaseController implements ICommonController<RealNameOwnerSearchRequest, RealNameOwnerRequest, RealNameOwnerResponse>, Serializable {

    @Inject
    private RealNameOwnerService realNameOwnerService;

    @Override
    @GetMapping("/list")
    public TableDataInfo<RealNameOwnerResponse> getAdminList(RealNameOwnerSearchRequest request, PageRequest pageRequest) {
        realNameOwnerService.getAdminList(request);
        return null;
    }

    @Override
    @GetMapping("/info")
    public CommonResponse<RealNameOwnerResponse> getInfoByHash(String hash) {
        realNameOwnerService.getInfoByHash(hash);
        return null;
    }

    @Override
    @PostMapping("/save")
    public CommonResponse<Boolean> create(RealNameOwnerRequest realNameOwnerRequest) {
        realNameOwnerService.create(realNameOwnerRequest);
        return null;
    }

    @Override
    @DeleteMapping("/")
    public CommonResponse<Boolean> softDeleteByHash(String hash) {
        return null;
    }

    @Override
    @DeleteMapping("/delete")
    public CommonResponse<Boolean> deleteByHash(String hash) {
        realNameOwnerService.deleteByHash(hash);
        return null;
    }

    @Override
    @PutMapping("/update")
    public CommonResponse<Boolean> updateEntity(RealNameOwnerRequest realNameOwnerRequest) {
//        realNameOwnerService.updateCategory(realNameOwnerRequest);
        return null;
    }
}
