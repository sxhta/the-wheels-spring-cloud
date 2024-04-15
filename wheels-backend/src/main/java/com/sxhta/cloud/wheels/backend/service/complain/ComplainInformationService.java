package com.sxhta.cloud.wheels.backend.service.complain;

import com.baomidou.mybatisplus.extension.service.IService;
import com.sxhta.cloud.common.service.ICommonService;
import com.sxhta.cloud.wheels.backend.entity.complain.ComplainInformation;
import com.sxhta.cloud.wheels.backend.request.complain.ComplainInformationRequest;
import com.sxhta.cloud.wheels.backend.request.complain.ComplainInformationSearchRequest;
import com.sxhta.cloud.wheels.backend.response.complain.ComplainInformationResponse;

public interface ComplainInformationService extends ICommonService<ComplainInformationSearchRequest, ComplainInformationRequest, ComplainInformationResponse>, IService<ComplainInformation> {

    Boolean handleComplainInformation(ComplainInformationRequest complainInformationRequest);
}
