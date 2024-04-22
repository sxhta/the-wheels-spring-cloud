package com.sxhta.cloud.wheels.frontend.service.complain;

import com.baomidou.mybatisplus.extension.service.IService;
import com.sxhta.cloud.wheels.frontend.request.complain.ComplainInformationRequest;
import com.sxhta.cloud.wheels.frontend.response.complain.ComplainInformationResponse;
import com.sxhta.cloud.wheels.frontend.response.complain.ComplainTypeResponse;
import com.sxhta.cloud.wheels.remote.domain.complain.ComplainInformation;

import java.util.List;

public interface ComplainInformationService extends IService<ComplainInformation> {

    Boolean userComplainCreate(ComplainInformationRequest request);

    List<ComplainInformationResponse> userComplainList();

    void driverComplainList();

    ComplainInformationResponse getComplainInfo(String hash);

    List<ComplainTypeResponse> getComplainTypeList();
}
