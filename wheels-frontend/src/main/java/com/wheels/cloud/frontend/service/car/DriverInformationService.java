package com.wheels.cloud.frontend.service.car;

import com.sxhta.cloud.common.service.ICommonService;
import com.wheels.cloud.frontend.request.car.DriverInformationRequest;
import com.wheels.cloud.frontend.request.car.DriverInformationSearchRequest;
import com.wheels.cloud.frontend.response.car.DriverInformationResponse;

public interface DriverInformationService extends ICommonService<DriverInformationSearchRequest, DriverInformationRequest, DriverInformationResponse> {
}
