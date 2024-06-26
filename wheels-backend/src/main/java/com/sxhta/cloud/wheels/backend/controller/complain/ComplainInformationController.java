package com.sxhta.cloud.wheels.backend.controller.complain;

import com.sxhta.cloud.common.web.controller.BaseController;
import com.sxhta.cloud.common.web.controller.ICommonController;
import com.sxhta.cloud.common.web.domain.CommonResponse;
import com.sxhta.cloud.common.web.page.PageRequest;
import com.sxhta.cloud.common.web.page.TableDataInfo;
import com.sxhta.cloud.wheels.backend.request.complain.ComplainInformationRequest;
import com.sxhta.cloud.wheels.backend.request.complain.ComplainInformationSearchRequest;
import com.sxhta.cloud.wheels.backend.response.complain.ComplainInformationResponse;
import com.sxhta.cloud.wheels.backend.service.complain.ComplainInformationService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.inject.Inject;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.io.Serializable;

@RestController
@RequestMapping("/complain")
@Tag(name = "投诉信息", description = "投诉信息管理器")
public class ComplainInformationController extends BaseController implements ICommonController<ComplainInformationSearchRequest, ComplainInformationRequest, ComplainInformationResponse>, Serializable {

    @Inject
    private ComplainInformationService complainInformationService;


    @Override
    @GetMapping("/list")
    public TableDataInfo<ComplainInformationResponse> getAdminList(ComplainInformationSearchRequest request, @Validated PageRequest pageRequest) {
        startPage(pageRequest);
        final var list = complainInformationService.getAdminList(request);
        return CommonResponse.list(list);
    }

    @Override
    @GetMapping("/info")
    public CommonResponse<ComplainInformationResponse> getInfoByHash(@RequestParam String hash) {
        return CommonResponse.success("查询成功", complainInformationService.getInfoByHash(hash));
    }

    @Override
    public CommonResponse<Boolean> create(ComplainInformationRequest complainInformationRequest) {
        complainInformationService.create(complainInformationRequest);
        return null;
    }

    @Override
    @DeleteMapping("/delete")
    public CommonResponse<Boolean> softDeleteByHash(@RequestParam String hash) {
        return CommonResponse.result(complainInformationService.softDeleteByHash(hash));
    }

    @Override
    public CommonResponse<Boolean> deleteByHash(String hash) {
        complainInformationService.deleteByHash(hash);
        return null;
    }

    @Override
    public CommonResponse<Boolean> updateEntity(ComplainInformationRequest complainInformationRequest) {
        complainInformationService.updateEntity(complainInformationRequest);
        return null;
    }

    @PutMapping("/handle")
    public CommonResponse<Boolean> handleComplainInformation(@RequestBody ComplainInformationRequest complainInformationRequest) {
        return CommonResponse.result(complainInformationService.handleComplainInformation(complainInformationRequest));
    }


}
