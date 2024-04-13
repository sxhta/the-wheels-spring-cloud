package com.sxhta.cloud.wheels.controller.car;

import com.sxhta.cloud.common.web.controller.ICommonController;
import com.sxhta.cloud.common.web.domain.CommonResponse;
import com.sxhta.cloud.common.web.page.PageRequest;
import com.sxhta.cloud.common.web.page.TableDataInfo;
import com.sxhta.cloud.wheels.request.car.CarTypeCategoryRequest;
import com.sxhta.cloud.wheels.request.car.CarTypeCategorySearchRequest;
import com.sxhta.cloud.wheels.response.car.CarTypeCategoryResponse;
import com.sxhta.cloud.wheels.service.car.CarTypeCategoryService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.inject.Inject;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.Serializable;

import static com.github.pagehelper.page.PageMethod.startPage;

@RestController
@RequestMapping("/car/type/category")
@Tag(name = "车辆类型型号", description = "车辆类型型号管理器")
public class CarTypeCategoryController implements ICommonController<CarTypeCategorySearchRequest, CarTypeCategoryRequest, CarTypeCategoryResponse>, Serializable {

    @Inject
    private CarTypeCategoryService carTypeCategoryService;


    @Override
    @PostMapping("/list")
    public TableDataInfo<CarTypeCategoryResponse> getAdminList(CarTypeCategorySearchRequest request, PageRequest pageRequest) {
        startPage(pageRequest);
        final var list = carTypeCategoryService.getAdminList(request);
        return CommonResponse.list(list);
    }

    @Override
    @PostMapping("/info")
    public CommonResponse<CarTypeCategoryResponse> getInfoByHash(String hash) {
        return CommonResponse.success("查询成功", carTypeCategoryService.getInfoByHash(hash));
    }

    @Override
    @PostMapping("/save")
    public CommonResponse<Boolean> create(CarTypeCategoryRequest carTypeCategoryRequest) {
        return CommonResponse.result(carTypeCategoryService.create(carTypeCategoryRequest));
    }

    @Override
    @PostMapping("/delete")
    public CommonResponse<Boolean> softDeleteByHash(String hash) {
        return CommonResponse.result(carTypeCategoryService.softDeleteByHash(hash));
    }

    @Override
    public CommonResponse<Boolean> deleteByHash(String hash) {
        return null;
    }

    @Override
    @PostMapping("/update")
    public CommonResponse<Boolean> updateEntity(CarTypeCategoryRequest carTypeCategoryRequest) {
        return CommonResponse.result(carTypeCategoryService.updateEntity(carTypeCategoryRequest));
    }
}
