package com.sxhta.cloud.common.service;

import java.io.Serializable;
import java.util.List;

public interface ICommonService<Search extends Serializable, Request extends Serializable, Response extends Serializable> {

    Boolean create(Request request);

    Response getInfoByHash(String hash);

    Boolean softDeleteByHash(String hash);

    Boolean deleteByHash(String hash);

    Boolean updateCategory(Request request);

    List<Response> getAdminList(Search request);
}
