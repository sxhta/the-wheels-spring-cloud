package com.wheels.cloud.frontend.service.complain;

import com.baomidou.mybatisplus.extension.service.IService;
import com.sxhta.cloud.wheels.remote.domain.complain.ComplainInformation;
import com.wheels.cloud.frontend.request.complain.ComplainInformationRequest;
import com.wheels.cloud.frontend.response.complain.ComplainInformationResponse;

import java.util.List;

public interface ComplainInformationService extends IService<ComplainInformation> {

    Boolean userComplainCreate(ComplainInformationRequest request);

    List<ComplainInformationResponse> userComplainList();

    void driverComplainList();
}
