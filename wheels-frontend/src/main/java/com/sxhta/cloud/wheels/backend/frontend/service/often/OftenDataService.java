package com.sxhta.cloud.wheels.frontend.service.often;

import com.baomidou.mybatisplus.extension.service.IService;
import com.sxhta.cloud.common.service.ICommonService;
import com.sxhta.cloud.wheels.frontend.entity.often.OftenData;
import com.sxhta.cloud.wheels.frontend.request.often.OftenDataRequest;
import com.sxhta.cloud.wheels.frontend.request.often.OftenDataSearchRequest;
import com.sxhta.cloud.wheels.frontend.response.often.OftenDataResponse;

public interface OftenDataService extends ICommonService<OftenDataSearchRequest, OftenDataRequest, OftenDataResponse>, IService<OftenData> {

    String getCurrentUserHash();
}
