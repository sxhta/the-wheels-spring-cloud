package com.sxhta.cloud.wheels.auth.form.owner;

import com.sxhta.cloud.security.request.AbstractSignRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 用户登录对象
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class FrontOwnerLoginRequest extends AbstractSignRequest {
}
