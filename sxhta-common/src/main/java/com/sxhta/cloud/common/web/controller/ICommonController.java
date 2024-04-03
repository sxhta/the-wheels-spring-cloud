package com.sxhta.cloud.common.web.controller;

import com.sxhta.cloud.common.web.domain.CommonResponse;
import com.sxhta.cloud.common.web.page.PageRequest;
import com.sxhta.cloud.common.web.page.TableDataInfo;

import java.io.Serializable;

public interface ICommonController<Search extends Serializable, Request extends Serializable, Response extends Serializable> {

    TableDataInfo<Response> getAdminList(Search request, PageRequest pageRequest);

    CommonResponse<Response> getInfoByHash(String hash);

    CommonResponse<Boolean> create(Request request);

    CommonResponse<Boolean> softDeleteByHash(String hash);

    CommonResponse<Boolean> deleteByHash(String hash);

    CommonResponse<Boolean> updateCategory(Request request);

}