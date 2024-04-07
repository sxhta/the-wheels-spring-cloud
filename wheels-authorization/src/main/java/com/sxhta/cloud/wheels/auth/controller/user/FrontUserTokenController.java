package com.sxhta.cloud.wheels.auth.controller.user;

import cn.hutool.core.util.ObjectUtil;
import com.sxhta.cloud.common.exception.CommonException;
import com.sxhta.cloud.common.web.domain.CommonResponse;
import com.sxhta.cloud.security.response.TokenResponse;
import com.sxhta.cloud.security.service.TokenService;
import com.sxhta.cloud.wheels.auth.request.CheckCodeRequest;
import com.sxhta.cloud.wheels.auth.service.sms.SmsService;
import com.sxhta.cloud.wheels.auth.service.user.FrontUserLoginService;
import com.sxhta.cloud.wheels.remote.domain.user.WheelsFrontUser;
import com.sxhta.cloud.wheels.remote.vo.FrontUserCacheVo;
import jakarta.inject.Inject;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


/**
 * token 控制
 */
@RestController
@RequestMapping(value = "/user")
public class FrontUserTokenController {

    @Inject
    private TokenService<FrontUserCacheVo, WheelsFrontUser> frontUserTokenService;

    @Inject
    private FrontUserLoginService<FrontUserCacheVo, WheelsFrontUser> frontUserLoginService;

    @Inject
    private SmsService smsService;

    @PostMapping(value = "/code/send")
    public CommonResponse<Boolean> sendCode(@RequestParam String phone) {
        if (smsService.sendCode(phone)) {
            return CommonResponse.success("发送成功");
        } else {
            return CommonResponse.error("发送失败");
        }
    }

    @PostMapping(value = "/sign")
    public CommonResponse<TokenResponse> loginOrRegisterByPhone(@Validated @RequestBody CheckCodeRequest request) {
        final var cacheVo = frontUserLoginService.loginOrRegisterByPhone(request);
        final var tokenResponse = frontUserTokenService.createToken(cacheVo);
        return CommonResponse.success(tokenResponse);
    }

    @DeleteMapping("logout")
    public CommonResponse<Boolean> logout(HttpServletRequest request) {
        final var token = frontUserTokenService.getToken(request);
        if (ObjectUtil.isNotEmpty(token)) {
            // 删除用户缓存记录
            final var isDelete = frontUserTokenService.deleteLoginUser(token);
            if (!isDelete) {
                throw new CommonException("用户缓存删除失败");
            }
        }
        return CommonResponse.success();
    }
}
