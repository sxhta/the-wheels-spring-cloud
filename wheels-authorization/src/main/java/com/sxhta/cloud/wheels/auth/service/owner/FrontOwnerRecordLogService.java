package com.sxhta.cloud.wheels.auth.service.owner;

public interface FrontOwnerRecordLogService {

    void recordLoginInfo(String username, String status, String message);
}
