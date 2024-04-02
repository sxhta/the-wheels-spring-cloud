package com.sxhta.cloud.auth.service;

public interface SystemRecordLogService {

    void recordLoginInfo(String username, String status, String message);
}
