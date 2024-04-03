package com.sxhta.cloud.wheels.auth.vo;

import com.sxhta.cloud.common.domain.AbstractUserCacheVo;
import com.sxhta.cloud.wheels.remote.domain.user.WheelsFrontUser;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;
import java.io.Serializable;

@Data
@EqualsAndHashCode(callSuper = false)
public class FrontUserCacheVo extends AbstractUserCacheVo<WheelsFrontUser> implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;
}
