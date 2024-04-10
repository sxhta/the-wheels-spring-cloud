package com.sxhta.cloud.common.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

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
        final var objectMapper = new ObjectMapper();
        try {
            return objectMapper.writeValueAsString(list);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
