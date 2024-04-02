package com.wheels.cloud.frontend.service.auth.impl;

import com.wheels.cloud.frontend.service.user.FrontUserService;
import com.wheels.cloud.frontend.vo.auth.UserDetailsVo;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final Logger logger = LoggerFactory.getLogger(UserDetailsServiceImpl.class);

    @Inject
    private FrontUserService frontUserService;

    @Override
    public UserDetails loadUserByUsername(final String username) throws UsernameNotFoundException {
        final var frontUser = frontUserService.getUserByAccount(username);
        if (!frontUser.getStatus()) {
            logger.info("登录用户：{} 已被停用.", username);
            throw new UsernameNotFoundException("对不起，您的账号：" + username + " 已停用");
        }
        return new UserDetailsVo(frontUser);
    }
}