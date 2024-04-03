package com.sxhta.cloud.wheels.auth.form.owner;

import com.sxhta.cloud.security.request.AbstractSignRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 用户注册对象
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class FrontOwnerRegisterRequest extends AbstractSignRequest {
}
