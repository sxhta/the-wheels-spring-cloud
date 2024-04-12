package com.wheels.cloud.frontend.service.bank.impl;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sxhta.cloud.common.exception.CommonNullException;
import com.sxhta.cloud.wheels.remote.domain.bank.BankCard;
import com.wheels.cloud.frontend.mapper.bank.BankCardMapper;
import com.wheels.cloud.frontend.request.bank.BankCardRequest;
import com.wheels.cloud.frontend.response.bank.BankCardResponse;
import com.wheels.cloud.frontend.service.bank.BankCardService;
import com.wheels.cloud.frontend.service.user.FrontUserInfoService;
import jakarta.inject.Inject;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class BankCardServiceImpl extends ServiceImpl<BankCardMapper, BankCard>
        implements BankCardService {

    @Inject
    private FrontUserInfoService frontUserInfoService;

    @Override
    public List<BankCardResponse> getListByToken() {
        final var userHash = frontUserInfoService.getUserHashByToken();
        final var lqw = new LambdaQueryWrapper<BankCard>();
        lqw.eq(BankCard::getType, 1)
                .eq(BankCard::getFrontUserHash, userHash)
                .isNull(BankCard::getDeleteTime);
        final var list = list(lqw);
        final var responseList = new ArrayList<BankCardResponse>();
        list.forEach(item -> {
            final var response = new BankCardResponse();
            BeanUtils.copyProperties(item, response);
            responseList.add(response);
        });
        return responseList;
    }

    @Override
    public BankCard getCardByHash(String hash) {
        final var lqw = new LambdaQueryWrapper<BankCard>();
        lqw.eq(BankCard::getHash, hash)
                .eq(BankCard::getType, 1)
                .isNull(BankCard::getDeleteTime);
        final var card = getOne(lqw);
        if (ObjectUtil.isEmpty(card)) {
            throw new CommonNullException("该银行卡不存在");
        }
        return card;
    }

    @Override
    public BankCardResponse getCardResponse(String hash) {
        final var card = getCardByHash(hash);
        final var response = new BankCardResponse();
        BeanUtils.copyProperties(card, response);
        return response;
    }

    @Override
    public Boolean create(BankCardRequest request) {
        final var userHash = frontUserInfoService.getUserHashByToken();
        final var card = new BankCard();
        BeanUtils.copyProperties(request, card);
        card.setFrontUserHash(userHash);
        return save(card);
    }

    @Override
    public Boolean softDelete(String hash) {
        final var currentCard = getCardByHash(hash);
        currentCard.setDeleteTime(LocalDateTime.now());
        return updateById(currentCard);
    }

    @Override
    public Boolean delete(String hash) {
        final var currentCard = getCardByHash(hash);
        currentCard.setDeleteTime(LocalDateTime.now());
        return removeById(currentCard);
    }

    @Override
    public Boolean updateEntity(BankCardRequest request) {
        final var userHash = frontUserInfoService.getUserHashByToken();
        final var card = new BankCard();
        BeanUtils.copyProperties(request, card);
        card.setFrontUserHash(userHash);
        return updateById(card);
    }
}
