package com.sxhta.cloud.wheels.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.sxhta.cloud.common.service.ICommonService;
import com.sxhta.cloud.wheels.entity.complain.ComplainInformation;
import com.sxhta.cloud.wheels.request.complain.ComplainInformationRequest;
import com.sxhta.cloud.wheels.request.complain.ComplainInformationSearchRequest;
import com.sxhta.cloud.wheels.response.complain.ComplainInformationResponse;

public interface ComplainInformationService extends ICommonService<ComplainInformationSearchRequest, ComplainInformationRequest, ComplainInformationResponse>, IService<ComplainInformation> {

}
