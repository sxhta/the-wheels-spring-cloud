package com.wheels.cloud.frontend.service.often;

import com.baomidou.mybatisplus.extension.service.IService;
import com.sxhta.cloud.common.service.ICommonService;
import com.wheels.cloud.frontend.entity.often.OftenData;
import com.wheels.cloud.frontend.request.often.OftenDataRequest;
import com.wheels.cloud.frontend.request.often.OftenDataSearchRequest;
import com.wheels.cloud.frontend.response.often.OftenDataResponse;

public interface OftenDataService extends ICommonService<OftenDataSearchRequest, OftenDataRequest, OftenDataResponse>, IService<OftenData> {

}
