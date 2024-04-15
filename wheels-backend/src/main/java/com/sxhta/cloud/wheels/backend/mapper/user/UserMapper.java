package com.sxhta.cloud.wheels.backend.mapper.user;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sxhta.cloud.wheels.remote.domain.user.WheelsFrontUser;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper extends BaseMapper<WheelsFrontUser> {


}
