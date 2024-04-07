package com.sxhta.cloud.payment.service.impl;

import com.sxhta.cloud.payment.service.IPayService;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service(value = "wechatPayService")
public class WechatPayServiceImpl implements IPayService<Map<String,Object>> {

    @Override
    public Map<String, Object> pay(String orderNo) {
        return Map.of();
    }
}
