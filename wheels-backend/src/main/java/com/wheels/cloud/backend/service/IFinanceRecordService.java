package com.wheels.cloud.backend.service;

import com.wheels.cloud.backend.request.FinanceRecordDto;

public interface IFinanceRecordService {
    Boolean saveFinanceRecord(FinanceRecordDto financeRecordDto);

    Boolean deleteFinanceRecord(String financeRecordCode);

    Boolean updateFinanceRecord(FinanceRecordDto financeRecordDto);

    Object selectFinanceRecordAll();

    Object selectFinanceRecordInfo(String financeRecordCode);
}
