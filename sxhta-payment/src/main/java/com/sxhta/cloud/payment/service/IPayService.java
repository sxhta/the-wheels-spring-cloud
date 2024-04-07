package com.sxhta.cloud.payment.service;

public interface IPayService<Response> {

    Response pay(String orderNo);
}
 