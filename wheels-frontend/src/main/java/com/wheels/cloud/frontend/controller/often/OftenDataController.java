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
import java.util.List;

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
    public TableDataInfo<OftenDataResponse> getAdminList(OftenDataSearchRequest request, PageRequest pageRequest) {
        List<OftenDataResponse> adminList = oftenDataService.getAdminList(request);
        return null;
    }

    @Override
    public CommonResponse<OftenDataResponse> getInfoByHash(String hash) {
        OftenDataResponse infoByHash = oftenDataService.getInfoByHash(hash);
        return null;
    }

    @Override
    @PostMapping("/save")
    public CommonResponse<Boolean> create(@RequestBody OftenDataRequest oftenDataRequest) {
        return CommonResponse.result(oftenDataService.create(oftenDataRequest));
    }

    @GetMapping("/user")
    public CommonResponse<String> getCurrentUserHash() {
        return CommonResponse.success("用户Hash", oftenDataService.getCurrentUserHash());
    }

    @Override
    public CommonResponse<Boolean> softDeleteByHash(String hash) {
        oftenDataService.softDeleteByHash(hash);
        return null;
    }

    @Override
    public CommonResponse<Boolean> deleteByHash(String hash) {
        return null;
    }

    @Override
    public CommonResponse<Boolean> updateEntity(OftenDataRequest oftenDataRequest) {
        oftenDataService.updateEntity(oftenDataRequest);
        return null;
    }
}
