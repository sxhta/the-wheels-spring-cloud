package com.sxhta.cloud.wheels.service.car;

import com.baomidou.mybatisplus.extension.service.IService;
import com.sxhta.cloud.common.service.ICommonService;
import com.sxhta.cloud.wheels.entity.car.CarTypeCategory;
import com.sxhta.cloud.wheels.request.car.CarTypeCategoryRequest;
import com.sxhta.cloud.wheels.request.car.CarTypeCategorySearchRequest;
import com.sxhta.cloud.wheels.response.car.CarTypeCategoryResponse;

public interface CarTypeCategoryService extends ICommonService<CarTypeCategorySearchRequest, CarTypeCategoryRequest, CarTypeCategoryResponse>, IService<CarTypeCategory> {
}
