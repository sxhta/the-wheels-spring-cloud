package com.sxhta.cloud.wheels.order.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sxhta.cloud.wheels.remote.domain.order.Order;
import org.apache.ibatis.annotations.Mapper;

/**
 * 订单管理 Mapper 接口
 */
@Mapper
public interface OrderMapper extends BaseMapper<Order> {

//    @Select("SELECT * FROM `order` WHERE pay_ ORDER BY create_time DESC LIMIT 2")
//    public Order getTwoOrder();
}
