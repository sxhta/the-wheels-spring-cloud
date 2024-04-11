package com.sxhta.cloud.wheels.service.complain;

import com.baomidou.mybatisplus.extension.service.IService;
import com.sxhta.cloud.common.service.ICommonService;
import com.sxhta.cloud.wheels.entity.complain.ComplainType;
import com.sxhta.cloud.wheels.request.complain.ComplainTypeRequest;
import com.sxhta.cloud.wheels.request.complain.ComplainTypeSearchRequest;
import com.sxhta.cloud.wheels.response.complain.ComplainTypeResponse;

public interface ComplainTypeService extends ICommonService<ComplainTypeSearchRequest, ComplainTypeRequest, ComplainTypeResponse>, IService<ComplainType> {

    Boolean updateStatus(String hash);

    ComplainType getEntity(String hash);
}
