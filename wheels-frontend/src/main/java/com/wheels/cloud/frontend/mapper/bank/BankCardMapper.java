package com.wheels.cloud.frontend.mapper.bank;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sxhta.cloud.wheels.remote.domain.bank.BankCard;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface BankCardMapper extends BaseMapper<BankCard> {
}
