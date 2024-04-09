package com.sxhta.cloud.wheels.controller.complain;

import com.sxhta.cloud.common.web.controller.BaseController;
import com.sxhta.cloud.common.web.controller.ICommonController;
import com.sxhta.cloud.common.web.domain.CommonResponse;
import com.sxhta.cloud.common.web.page.PageRequest;
import com.sxhta.cloud.common.web.page.TableDataInfo;
import com.sxhta.cloud.wheels.request.complain.ComplainTypeRequest;
import com.sxhta.cloud.wheels.request.complain.ComplainTypeSearchRequest;
import com.sxhta.cloud.wheels.response.complain.ComplainTypeResponse;
import com.sxhta.cloud.wheels.service.complain.ComplainTypeService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.inject.Inject;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.Serializable;

@RestController
@RequestMapping("/complain/type")
@Tag(name = "投诉类型", description = "投诉类型管理器")
public class ComplainTypeController extends BaseController implements ICommonController<ComplainTypeSearchRequest, ComplainTypeRequest, ComplainTypeResponse>, Serializable {

    @Inject
    private ComplainTypeService complainTypeService;


    @Override
    public TableDataInfo<ComplainTypeResponse> getAdminList(ComplainTypeSearchRequest request, PageRequest pageRequest) {
        complainTypeService.getAdminList(request);
        return null;
    }

    @Override
    public CommonResponse<ComplainTypeResponse> getInfoByHash(String hash) {
        complainTypeService.getInfoByHash(hash);
        return null;
    }

    @Override
    public CommonResponse<Boolean> create(ComplainTypeRequest complainTypeRequest) {
        complainTypeService.create(complainTypeRequest);
        return null;
    }

    @Override
    public CommonResponse<Boolean> softDeleteByHash(String hash) {
        complainTypeService.softDeleteByHash(hash);
        return null;
    }

    @Override
    public CommonResponse<Boolean> deleteByHash(String hash) {
        complainTypeService.deleteByHash(hash);
        return null;
    }

    @Override
    public CommonResponse<Boolean> updateCategory(ComplainTypeRequest complainTypeRequest) {
        complainTypeService.updateEntity(complainTypeRequest);
        return null;
    }
}
