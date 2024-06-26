package com.sxhta.cloud.wheels.backend.controller.car;

import com.sxhta.cloud.common.web.controller.BaseController;
import com.sxhta.cloud.common.web.controller.ICommonController;
import com.sxhta.cloud.common.web.domain.CommonResponse;
import com.sxhta.cloud.common.web.page.PageRequest;
import com.sxhta.cloud.common.web.page.TableDataInfo;
import com.sxhta.cloud.wheels.backend.entity.car.CarType;
import com.sxhta.cloud.wheels.backend.request.car.CarTypeRequest;
import com.sxhta.cloud.wheels.backend.request.car.CarTypeSearchRequest;
import com.sxhta.cloud.wheels.backend.response.car.CarTypeResponse;
import com.sxhta.cloud.wheels.backend.service.car.CarTypeService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.inject.Inject;
import org.springframework.web.bind.annotation.*;

import java.io.Serializable;
import java.util.List;

@RestController
@RequestMapping("/car/type")
@Tag(name = "车辆类型", description = "车辆类型管理器")
public class CarTypeController extends BaseController implements ICommonController<CarTypeSearchRequest, CarTypeRequest, CarTypeResponse>, Serializable {

    @Inject
    private CarTypeService carTypeService;

    @Override
    @GetMapping("/list")
    public TableDataInfo<CarTypeResponse> getAdminList(@ModelAttribute("CarTypeSearchRequest") CarTypeSearchRequest request, PageRequest pageRequest) {
        startPage(pageRequest);
        List<CarTypeResponse> list = carTypeService.getAdminList(request);
        return CommonResponse.list(list);
    }

    @Override
    @GetMapping("/info")
    public CommonResponse<CarTypeResponse> getInfoByHash(@RequestParam String hash) {
        CarTypeResponse infoByHash = carTypeService.getInfoByHash(hash);
        return CommonResponse.success(infoByHash);
    }

    @Override
    @PostMapping("/save")
    public CommonResponse<Boolean> create(@RequestBody CarTypeRequest carTypeRequest) {
        Boolean result = carTypeService.create(carTypeRequest);
        return CommonResponse.result(result);
    }

    @Override
    @DeleteMapping("/soft")
    public CommonResponse<Boolean> softDeleteByHash(@RequestParam String hash) {
        Boolean result = carTypeService.softDeleteByHash(hash);
        return CommonResponse.result(result);
    }

    @Override
    @DeleteMapping("/delete")
    public CommonResponse<Boolean> deleteByHash(@RequestParam String hash) {
        Boolean result = carTypeService.deleteByHash(hash);
        return CommonResponse.result(result);
    }

    @Override
    @PutMapping("/update")
    public CommonResponse<Boolean> updateEntity(@RequestBody CarTypeRequest carTypeRequest) {
        Boolean result = carTypeService.updateEntity(carTypeRequest);
        return CommonResponse.result(result);
    }


    @GetMapping("/all")
    public CommonResponse<List<CarType>> getCarTypeAll() {
        return CommonResponse.success("查询成功", carTypeService.getCarTypeAll());
    }


}
