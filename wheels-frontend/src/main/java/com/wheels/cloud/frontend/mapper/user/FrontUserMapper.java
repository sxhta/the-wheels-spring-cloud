package com.wheels.cloud.frontend.mapper.user;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wheels.cloud.frontend.domain.user.FrontUser;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface FrontUserMapper extends BaseMapper<FrontUser> {
}
