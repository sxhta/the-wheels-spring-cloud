package com.wheels.cloud.frontend.controller.car;


import com.sxhta.cloud.common.web.controller.BaseController;
import com.sxhta.cloud.common.web.controller.ICommonController;
import com.sxhta.cloud.common.web.domain.CommonResponse;
import com.sxhta.cloud.common.web.page.PageRequest;
import com.sxhta.cloud.common.web.page.TableDataInfo;
import com.wheels.cloud.frontend.request.car.DriverInformationRequest;
import com.wheels.cloud.frontend.request.car.DriverInformationSearchRequest;
import com.wheels.cloud.frontend.response.car.DriverInformationResponse;
import com.wheels.cloud.frontend.service.car.DriverInformationService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.inject.Inject;
import org.springframework.web.bind.annotation.*;

import java.io.Serial;
import java.io.Serializable;

/**
 * 司机资料
 */
@RestController
@RequestMapping("/driver/information")
@Tag(name = "司机资料", description = "司机资料控制器")
public class DriverInformationController extends BaseController implements ICommonController<DriverInformationSearchRequest, DriverInformationRequest, DriverInformationResponse>, Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Inject
    private DriverInformationService driverInformationService;

    @Override
    @GetMapping("/list")
    public TableDataInfo<DriverInformationResponse> getAdminList(DriverInformationSearchRequest request, PageRequest pageRequest) {
        startPage(pageRequest);
        final var list = driverInformationService.getAdminList(request);
        return CommonResponse.list(list);
    }


    @Override
    @GetMapping("/info")
    public CommonResponse<DriverInformationResponse> getInfoByHash(@RequestParam("hash") String hash) {
        final var result = driverInformationService.getInfoByHash(hash);
        return CommonResponse.success(result);
    }

    @Override
    @PostMapping("/save")
    public CommonResponse<Boolean> create(@RequestBody DriverInformationRequest request) {
        final var result = driverInformationService.create(request);
        return CommonResponse.result(result);
    }

    @Override
    public CommonResponse<Boolean> softDeleteByHash(@RequestParam String hash) {
        final var result = driverInformationService.softDeleteByHash(hash);
        return CommonResponse.result(result);
    }

    @Override
    @DeleteMapping("/delete")
    public CommonResponse<Boolean> deleteByHash(@RequestParam String hash) {
        final var result = driverInformationService.deleteByHash(hash);
        return CommonResponse.result(result);
    }

    @Override
    @PutMapping("/update")
    public CommonResponse<Boolean> updateEntity(@RequestBody DriverInformationRequest request) {
        final var result = driverInformationService.updateEntity(request);
        return CommonResponse.result(result);
    }
}
