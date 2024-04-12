package com.wheels.cloud.frontend.controller.often;


import com.sxhta.cloud.common.web.controller.BaseController;
import com.sxhta.cloud.common.web.controller.ICommonController;
import com.sxhta.cloud.common.web.domain.CommonResponse;
import com.sxhta.cloud.common.web.page.PageRequest;
import com.sxhta.cloud.common.web.page.TableDataInfo;
import com.wheels.cloud.frontend.request.often.OftenDataRequest;
import com.wheels.cloud.frontend.request.often.OftenDataSearchRequest;
import com.wheels.cloud.frontend.response.often.OftenDataResponse;
import com.wheels.cloud.frontend.service.often.OftenDataService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.inject.Inject;
import org.springframework.web.bind.annotation.*;

import java.io.Serial;
import java.io.Serializable;

/**
 * 常用资料
 */
@RestController
@RequestMapping("/often/data")
@Tag(name = "常用资料", description = "常用资料控制器")
public class OftenDataController extends BaseController implements ICommonController<OftenDataSearchRequest, OftenDataRequest, OftenDataResponse>, Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Inject
    private OftenDataService oftenDataService;

    @Override
    @GetMapping("/list")
    public TableDataInfo<OftenDataResponse> getAdminList(@ModelAttribute("OftenDataSearchRequest") OftenDataSearchRequest request, PageRequest pageRequest) {
        startPage(pageRequest);
        final var list = oftenDataService.getAdminList(request);
        return CommonResponse.list(list);
    }

    @Override
    @GetMapping("/info")
    public CommonResponse<OftenDataResponse> getInfoByHash(@RequestParam String hash) {
        return CommonResponse.success("查询成功", oftenDataService.getInfoByHash(hash));
    }

    @Override
    @PostMapping("/save")
    public CommonResponse<Boolean> create(@RequestBody OftenDataRequest oftenDataRequest) {
        return CommonResponse.result(oftenDataService.create(oftenDataRequest));
    }


    @Override
    @DeleteMapping("/delete")
    public CommonResponse<Boolean> softDeleteByHash(@RequestParam("hash") String hash) {
        return CommonResponse.result(oftenDataService.softDeleteByHash(hash));
    }

    @Override
    public CommonResponse<Boolean> deleteByHash(String hash) {
        return null;
    }

    @Override
    @PutMapping("/update")
    public CommonResponse<Boolean> updateEntity(@RequestBody OftenDataRequest oftenDataRequest) {
        return CommonResponse.result(oftenDataService.updateEntity(oftenDataRequest));
    }
}
