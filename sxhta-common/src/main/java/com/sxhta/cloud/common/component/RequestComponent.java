package com.sxhta.cloud.common.component;

import jakarta.servlet.http.HttpServletRequest;

import java.util.HashMap;
import java.util.List;

public interface RequestComponent {

    HttpServletRequest getRequest();

    HashMap<String, Object> getRequestParamAndHeader();

    String getDomain();

    String getUri(HttpServletRequest request);

    List<String> stringToArrayStrRegex(String str, String regex);
}
