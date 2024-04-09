package com.sxhta.cloud.wheels.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.sxhta.cloud.common.service.ICommonService;
import com.sxhta.cloud.wheels.entity.complain.ComplainType;
import com.sxhta.cloud.wheels.request.complain.ComplainTypeRequest;
import com.sxhta.cloud.wheels.request.complain.ComplainTypeSearchRequest;
import com.sxhta.cloud.wheels.response.complain.ComplainTypeResponse;

public interface ComplainTypeService extends ICommonService<ComplainTypeSearchRequest, ComplainTypeRequest, ComplainTypeResponse>, IService<ComplainType> {

}
