package com.sxhta.cloud.wheels.auth.service.user;

public interface FrontUserRecordLogService {

    void recordLoginInfo(String username, String status, String message);
}
