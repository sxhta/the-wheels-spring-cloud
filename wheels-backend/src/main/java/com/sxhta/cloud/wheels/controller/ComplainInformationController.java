package com.sxhta.cloud.wheels.controller;

import com.sxhta.cloud.common.web.controller.BaseController;
import com.sxhta.cloud.common.web.controller.ICommonController;
import com.sxhta.cloud.common.web.domain.CommonResponse;
import com.sxhta.cloud.common.web.page.PageRequest;
import com.sxhta.cloud.common.web.page.TableDataInfo;
import com.sxhta.cloud.wheels.request.complain.ComplainInformationRequest;
import com.sxhta.cloud.wheels.request.complain.ComplainInformationSearchRequest;
import com.sxhta.cloud.wheels.response.complain.ComplainInformationResponse;
import com.sxhta.cloud.wheels.service.ComplainInformationService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.inject.Inject;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.Serializable;
import java.util.List;

@RestController
@RequestMapping("/complain")
@Tag(name = "投诉信息", description = "投诉信息管理器")
public class ComplainInformationController extends BaseController implements ICommonController<ComplainInformationSearchRequest, ComplainInformationRequest, ComplainInformationResponse>, Serializable {

    @Inject
    private ComplainInformationService complainInformationService;


    @Override
    public TableDataInfo<ComplainInformationResponse> getAdminList(ComplainInformationSearchRequest request, PageRequest pageRequest) {
        startPage(pageRequest);
        List<ComplainInformationResponse> list = complainInformationService.getAdminList(request);
        return CommonResponse.list(list);
    }

    @Override
    public CommonResponse<ComplainInformationResponse> getInfoByHash(String hash) {
        complainInformationService.getInfoByHash(hash);
        return null;
    }

    @Override
    public CommonResponse<Boolean> create(ComplainInformationRequest complainInformationRequest) {
        complainInformationService.create(complainInformationRequest);
        return null;
    }

    @Override
    public CommonResponse<Boolean> softDeleteByHash(String hash) {
        complainInformationService.softDeleteByHash(hash);
        return null;
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
}
