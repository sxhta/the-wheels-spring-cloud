package com.wheels.cloud.frontend.service.auth.impl;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.toolkit.SqlHelper;
import com.sxhta.cloud.common.component.JwtComponent;
import com.sxhta.cloud.common.constant.CacheConstants;
import com.sxhta.cloud.common.constant.SecurityConstants;
import com.sxhta.cloud.common.exception.CommonNullException;
import com.sxhta.cloud.common.exception.ServiceException;
import com.sxhta.cloud.common.component.impl.JwtComponentImpl;
import com.sxhta.cloud.security.service.AuthService;
import com.sxhta.cloud.security.service.SecurityService;
import com.wheels.cloud.frontend.domain.user.FrontUser;
import com.wheels.cloud.frontend.mapper.user.FrontUserMapper;
import com.wheels.cloud.frontend.request.auth.LoginRequest;
import com.wheels.cloud.frontend.request.auth.RegisterRequest;
import com.wheels.cloud.frontend.response.LoginResponse;
import com.wheels.cloud.frontend.response.RegisterResponse;
import com.wheels.cloud.frontend.service.auth.FrontAuthService;
import com.wheels.cloud.frontend.service.auth.FrontTokenService;
import com.wheels.cloud.frontend.vo.auth.UserDetailsVo;
import jakarta.inject.Inject;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.BeanUtils;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
public class FrontAuthServiceImpl implements FrontAuthService {

    private final static long expireTime = CacheConstants.EXPIRATION;

    @Inject
    private UserDetailsService userDetailsService;

    @Inject
    private FrontTokenService frontTokenService;

    @Inject
    private FrontUserMapper frontUserMapper;

    @Inject
    private AuthService authService;

    @Inject
    private SecurityService securityService;

    @Inject
    private JwtComponent jwtComponent;

    @Override
    public LoginResponse login(LoginRequest request) {
        final var requestAccount = request.getAccount();
        final var requestPassword = request.getPassword();
        final var lqw = new LambdaQueryWrapper<FrontUser>();
        lqw.eq(FrontUser::getAccount, requestAccount)
                .isNull(FrontUser::getDeleteTime);
        final var count = frontUserMapper.selectCount(lqw);
        if (count <= 0) {
            throw new ServiceException("该账户不存在");
        }
        final var userDetails = userDetailsService.loadUserByUsername(requestAccount);
        final var authorities = userDetails.getAuthorities();
        final var authentication =
                new UsernamePasswordAuthenticationToken(userDetails, requestPassword, authorities);
        final var userDetailsVo = (UserDetailsVo) authentication.getPrincipal();
        final var currentUser = userDetailsVo.getFrontUser();
        if (ObjectUtil.isNull(currentUser)) {
            throw new CommonNullException("授权系统中不存在用户信息");
        }
        final var dbPassword = currentUser.getPassword();
        final var checkPasswordResult = checkPassword(requestPassword, dbPassword);
        if (!checkPasswordResult) {
            throw new ServiceException("帐号或密码不正确");
        }
        final var userDetailsVoWithNewToken = frontTokenService.createToken(currentUser);
        final var response = new LoginResponse();
        BeanUtils.copyProperties(userDetailsVoWithNewToken, response);
        // Jwt存储信息
        final var claimsMap = new HashMap<String, Object>();
        final var token = userDetailsVoWithNewToken.getToken();
        final var userId = userDetailsVoWithNewToken.getUserId();
        final var userName = userDetailsVoWithNewToken.getAccount();
        claimsMap.put(SecurityConstants.USER_KEY, token);
        claimsMap.put(SecurityConstants.DETAILS_USER_ID, userId);
        claimsMap.put(SecurityConstants.DETAILS_USERNAME, userName);

        // 接口返回信息
        final var accessToken = jwtComponent.createToken(claimsMap);
        response.setAccessToken(accessToken)
                .setExpiresIn(expireTime);
        return response;
    }

    @Override
    public RegisterResponse register(RegisterRequest request) {
        final var account = request.getAccount();
        final var lqw = new LambdaQueryWrapper<FrontUser>();
        lqw.eq(FrontUser::getAccount, account);
        final var count = frontUserMapper.selectCount(lqw);
        if (count > 0) {
            throw new ServiceException("该账户已存在");
        }
        final var originPassword = request.getPassword();
        final var password = securityService.encryptPassword(originPassword);
        final var frontUser = new FrontUser();
        frontUser.setAccount(account)
                .setPassword(password)
                .setStatus(true);
        final var isSave = SqlHelper.retBool(frontUserMapper.insert(frontUser));
        if (isSave) {
            final var userId = frontUser.getId();
            final var userHash = frontUser.getHash();
            final var response = new RegisterResponse();
            response.setAccount(account)
                    .setUserId(userId)
                    .setUserHash(userHash);
            return response;
        }
        throw new ServiceException("创建用户失败");
    }

    @Override
    public Boolean checkPassword(String requestPassword, String dbPassword) {
        return securityService.matchesPassword(requestPassword, dbPassword);
    }

    @Override
    public Boolean logout(HttpServletRequest request) {
        final var jwtToken = frontTokenService.getJwtTokenByHttpServletRequest(request);
        if (StrUtil.isEmpty(jwtToken)) {
            throw new CommonNullException("登出所需jwt_token不存在");
        }
        // 删除用户缓存记录
        authService.logoutByToken(jwtToken);
        return true;
    }
}
