package com.sxhta.cloud.common.web.controller.contract;

import com.sxhta.cloud.common.web.domain.CommonResponse;

public interface SoftDeleteable {

    CommonResponse<Boolean> softDeleteByHash(String hash);
}
