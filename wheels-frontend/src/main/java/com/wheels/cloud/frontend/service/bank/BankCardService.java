package com.wheels.cloud.frontend.service.bank;

import com.baomidou.mybatisplus.extension.service.IService;
import com.sxhta.cloud.wheels.remote.domain.bank.BankCard;
import com.wheels.cloud.frontend.request.bank.BankCardRequest;
import com.wheels.cloud.frontend.response.bank.BankCardResponse;

import java.util.List;

public interface BankCardService extends IService<BankCard> {

    List<BankCardResponse> getListByToken();

    BankCard getCardByHash(String hash);

    BankCardResponse getCardResponse(String hash);

    Boolean create(BankCardRequest request);

    Boolean softDelete(String hash);

    Boolean delete(String hash);

    Boolean updateEntity(BankCardRequest request);
}
