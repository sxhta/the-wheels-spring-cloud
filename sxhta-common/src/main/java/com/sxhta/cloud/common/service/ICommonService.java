package com.sxhta.cloud.common.service;

import com.sxhta.cloud.common.utils.CharacterConvert;

import java.io.Serializable;
import java.util.List;

public interface ICommonService<Search extends Serializable, Request extends Serializable, Response extends Serializable> {

    Boolean create(Request request);

    Response getInfoByHash(String hash);

    Boolean softDeleteByHash(String hash);

    Boolean deleteByHash(String hash);

    Boolean updateEntity(Request request);

    List<Response> getAdminList(Search request);

    default String listToJsonString(List<String> list) {
        return CharacterConvert.listToJsonString(list);
    }

    default List<String> jsonStringToList(String jsonString) {
        return CharacterConvert.stringToJsonList(jsonString);
    }
}
