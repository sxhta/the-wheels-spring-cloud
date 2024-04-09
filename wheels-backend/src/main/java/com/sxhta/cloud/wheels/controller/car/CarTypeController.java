package com.sxhta.cloud.wheels.controller.car;

import com.sxhta.cloud.common.web.controller.BaseController;
import com.sxhta.cloud.common.web.controller.ICommonController;
import com.sxhta.cloud.common.web.domain.CommonResponse;
import com.sxhta.cloud.common.web.page.PageRequest;
import com.sxhta.cloud.common.web.page.TableDataInfo;
import com.sxhta.cloud.wheels.entity.car.CarType;
import com.sxhta.cloud.wheels.request.car.CarTypeRequest;
import com.sxhta.cloud.wheels.request.car.CarTypeSearchRequest;
import com.sxhta.cloud.wheels.response.CarTypeResponse;
import com.sxhta.cloud.wheels.service.car.CarTypeService;
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
    public TableDataInfo<CarTypeResponse> getAdminList(CarTypeSearchRequest request, PageRequest pageRequest) {
        List<CarTypeResponse> list = carTypeService.getAdminList(request);
        return CommonResponse.list(list);
    }

    @Override
    @GetMapping("/info")
    public CommonResponse<CarTypeResponse> getInfoByHash(String hash) {
        CarTypeResponse infoByHash = carTypeService.getInfoByHash(hash);
        return CommonResponse.success(infoByHash);
    }

    @Override
    @PostMapping("/save")
    public CommonResponse<Boolean> create(CarTypeRequest carTypeRequest) {
        Boolean result = carTypeService.create(carTypeRequest);
        return CommonResponse.result(result);
    }

    @Override
    @DeleteMapping("/soft")
    public CommonResponse<Boolean> softDeleteByHash(String hash) {
        Boolean result = carTypeService.softDeleteByHash(hash);

        return CommonResponse.result(result);
    }

    @Override
    @DeleteMapping("/delete")
    public CommonResponse<Boolean> deleteByHash(String hash) {
        Boolean result = carTypeService.deleteByHash(hash);
        return CommonResponse.result(result);
    }

    @Override
    @PutMapping("/update")
    public CommonResponse<Boolean> updateCategory(CarTypeRequest carTypeRequest) {
        Boolean result = carTypeService.updateEntity(carTypeRequest);
        return CommonResponse.result(result);
    }


    @GetMapping("/all")
    public List<CarType> getCarTypeAll() {
        return carTypeService.getCarTypeAll();
    }


}
