package com.wheels.cloud.frontend.service.auth;

import com.wheels.cloud.frontend.domain.user.FrontUser;
import com.wheels.cloud.frontend.vo.auth.UserDetailsVo;
import jakarta.servlet.http.HttpServletRequest;

public interface FrontTokenService {

    UserDetailsVo createToken(FrontUser frontUser);

    UserDetailsVo refreshToken(UserDetailsVo response);

    UserDetailsVo getUserDetailsVoByHttpServletRequest(HttpServletRequest request);

    String getJwtTokenByHttpServletRequest(HttpServletRequest request);

    String getTokenKey(String token);

    Boolean deleteToken(String tokenKey);
}
