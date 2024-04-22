package com.sxhta.cloud.wheels.frontend.service.complain;

import com.baomidou.mybatisplus.extension.service.IService;
import com.sxhta.cloud.wheels.remote.domain.complain.ComplainType;

import java.util.List;

public interface ComplainTypeService extends IService<ComplainType> {

    List<ComplainType> getComplainTypeList();
}
