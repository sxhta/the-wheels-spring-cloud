package com.sxhta.cloud.common.web.controller.contract;

import com.sxhta.cloud.common.web.page.PageRequest;
import com.sxhta.cloud.common.web.page.TableDataInfo;

public interface Listable<Search, Response> {

    TableDataInfo<Response> getAdminList(Search request, PageRequest pageRequest);
}
