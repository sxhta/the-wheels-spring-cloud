package com.sxhta.cloud.wheels.backend.service.car;

import com.baomidou.mybatisplus.extension.service.IService;
import com.sxhta.cloud.common.service.ICommonService;
import com.sxhta.cloud.wheels.backend.entity.car.CarType;
import com.sxhta.cloud.wheels.backend.request.car.CarTypeRequest;
import com.sxhta.cloud.wheels.backend.request.car.CarTypeSearchRequest;
import com.sxhta.cloud.wheels.backend.response.car.CarTypeResponse;

import java.util.List;

public interface CarTypeService extends ICommonService<CarTypeSearchRequest, CarTypeRequest, CarTypeResponse>, IService<CarType> {
    List<CarType> getCarTypeAll();
}
