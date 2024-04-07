package com.sxhta.cloud.payment.service.impl;

import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.sxhta.cloud.payment.constant.Constants;
import com.sxhta.cloud.payment.service.IPayService;
import jakarta.annotation.Nonnull;
import org.springframework.stereotype.Service;

@Service(value = "alipayService")
public class AlipayServiceImpl implements IPayService<String> {


    @Nonnull
    private AlipayClient getClient() {
        //2.获取参数 (公共参数和请求参数)
        //获取支付宝的appid
        final var appId = alipayConfig.appId;
        //获取商户的私钥
        final var merchantPrivateKey = alipayConfig.merchantPrivateKey;
        //获取阿里的公钥
        final var alipayPublicKey = alipayConfig.alipayPublicKey;
        //编码
        final var charset = alipayConfig.charset;
        //签名方式
        final var signType = alipayConfig.signType;
        //网关
        final var gatewayUrl = alipayConfig.gatewayUrl;
        //3.获取客户端对象
        return new DefaultAlipayClient(
                gatewayUrl,
                appId,
                merchantPrivateKey,
                Constants.FORMAT,
                charset,
                alipayPublicKey,
                signType);
    }


    @Override
    public String pay(String orderNo) {
        return "";
    }
}
