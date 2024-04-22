package com.sxhta.cloud.wheels.frontend.service.car;

import com.sxhta.cloud.common.service.ICommonService;
import com.sxhta.cloud.wheels.frontend.request.car.DriverInformationRequest;
import com.sxhta.cloud.wheels.frontend.request.car.DriverInformationSearchRequest;
import com.sxhta.cloud.wheels.frontend.response.car.DriverInformationResponse;

public interface DriverInformationService extends ICommonService<DriverInformationSearchRequest, DriverInformationRequest, DriverInformationResponse> {
}
