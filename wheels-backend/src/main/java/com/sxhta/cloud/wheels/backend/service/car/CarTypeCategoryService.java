package com.sxhta.cloud.wheels.backend.service.car;

import com.baomidou.mybatisplus.extension.service.IService;
import com.sxhta.cloud.common.service.ICommonService;
import com.sxhta.cloud.wheels.backend.entity.car.CarTypeCategory;
import com.sxhta.cloud.wheels.backend.request.car.CarTypeCategoryRequest;
import com.sxhta.cloud.wheels.backend.request.car.CarTypeCategorySearchRequest;
import com.sxhta.cloud.wheels.backend.response.car.CarTypeCategoryResponse;

public interface CarTypeCategoryService extends ICommonService<CarTypeCategorySearchRequest, CarTypeCategoryRequest, CarTypeCategoryResponse>, IService<CarTypeCategory> {
}
