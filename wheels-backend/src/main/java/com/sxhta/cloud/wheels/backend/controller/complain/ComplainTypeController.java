package com.sxhta.cloud.wheels.backend.controller.complain;

import cn.hutool.core.util.StrUtil;
import com.sxhta.cloud.common.exception.ServiceException;
import com.sxhta.cloud.common.web.controller.BaseController;
import com.sxhta.cloud.common.web.controller.ICommonController;
import com.sxhta.cloud.common.web.domain.CommonResponse;
import com.sxhta.cloud.common.web.page.PageRequest;
import com.sxhta.cloud.common.web.page.TableDataInfo;
import com.sxhta.cloud.wheels.backend.request.complain.ComplainTypeRequest;
import com.sxhta.cloud.wheels.backend.request.complain.ComplainTypeSearchRequest;
import com.sxhta.cloud.wheels.backend.response.complain.ComplainTypeResponse;
import com.sxhta.cloud.wheels.backend.service.complain.ComplainTypeService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.inject.Inject;
import org.springframework.web.bind.annotation.*;

import java.io.Serializable;

@RestController
@RequestMapping("/complain/type")
@Tag(name = "投诉类型", description = "投诉类型管理器")
public class ComplainTypeController extends BaseController implements ICommonController<ComplainTypeSearchRequest, ComplainTypeRequest, ComplainTypeResponse>, Serializable {

    @Inject
    private ComplainTypeService complainTypeService;


    @Override
    @GetMapping("/list")
    public TableDataInfo<ComplainTypeResponse> getAdminList(@ModelAttribute("ComplainTypeSearchRequest") ComplainTypeSearchRequest request, PageRequest pageRequest) {
        startPage(pageRequest);
        final var list = complainTypeService.getAdminList(request);
        return CommonResponse.list(list);
    }

    @Override
    @GetMapping("/info")
    public CommonResponse<ComplainTypeResponse> getInfoByHash(@RequestParam String hash) {
        if (StrUtil.isBlank(hash)) {
            throw new ServiceException("该投诉类型异常，请联系管理员");
        }
        return CommonResponse.success("查询成功", complainTypeService.getInfoByHash(hash));
    }

    @Override
    @PostMapping("/save")
    public CommonResponse<Boolean> create(@RequestBody ComplainTypeRequest complainTypeRequest) {
        return CommonResponse.result(complainTypeService.create(complainTypeRequest));
    }

    @Override
    @DeleteMapping("/delete")
    public CommonResponse<Boolean> softDeleteByHash(@RequestParam String hash) {
        if (StrUtil.isBlank(hash)) {
            throw new ServiceException("该投诉类型异常，请联系管理员");
        }
        return CommonResponse.result(complainTypeService.softDeleteByHash(hash));
    }

    @Override
    public CommonResponse<Boolean> deleteByHash(String hash) {
        complainTypeService.deleteByHash(hash);
        return null;
    }

    @Override
    @PutMapping("/update")
    public CommonResponse<Boolean> updateEntity(@RequestBody ComplainTypeRequest complainTypeRequest) {
        return CommonResponse.result(complainTypeService.updateEntity(complainTypeRequest));
    }

    @PutMapping("/status")
    public CommonResponse<Boolean> updateStatus(@RequestParam String hash) {
        return CommonResponse.result(complainTypeService.updateStatus(hash));
    }

}
