package com.sxhta.cloud.wheels.backend.service.complain;

import com.baomidou.mybatisplus.extension.service.IService;
import com.sxhta.cloud.common.service.ICommonService;
import com.sxhta.cloud.wheels.backend.entity.complain.ComplainType;
import com.sxhta.cloud.wheels.backend.request.complain.ComplainTypeRequest;
import com.sxhta.cloud.wheels.backend.request.complain.ComplainTypeSearchRequest;
import com.sxhta.cloud.wheels.backend.response.complain.ComplainTypeResponse;

public interface ComplainTypeService extends ICommonService<ComplainTypeSearchRequest, ComplainTypeRequest, ComplainTypeResponse>, IService<ComplainType> {

    Boolean updateStatus(String hash);

    ComplainType getEntity(String hash);
}
