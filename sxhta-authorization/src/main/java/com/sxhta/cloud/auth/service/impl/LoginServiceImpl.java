package com.sxhta.cloud.auth.service.impl;


import cn.hutool.core.util.ObjectUtil;
import com.sxhta.cloud.auth.service.LoginService;
import com.sxhta.cloud.auth.service.SystemPasswordService;
import com.sxhta.cloud.auth.service.SystemRecordLogService;
import com.sxhta.cloud.cache.redis.service.RedisService;
import com.sxhta.cloud.common.component.IpComponent;
import com.sxhta.cloud.common.constant.CacheConstants;
import com.sxhta.cloud.common.constant.Constants;
import com.sxhta.cloud.common.constant.SecurityConstants;
import com.sxhta.cloud.common.constant.UserConstants;
import com.sxhta.cloud.common.enums.UserStatus;
import com.sxhta.cloud.common.exception.ServiceException;
import com.sxhta.cloud.common.text.Convert;
import com.sxhta.cloud.remote.RemoteUserOpenFeign;
import com.sxhta.cloud.remote.domain.SysUser;
import com.sxhta.cloud.remote.model.AbstractUserCacheVo;
import com.sxhta.cloud.remote.model.AbstractUserEntity;
import com.sxhta.cloud.security.service.SecurityService;
import jakarta.inject.Inject;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

/**
 * 登录校验方法
 */
@Service
public class LoginServiceImpl<Cache extends AbstractUserCacheVo<Entity>, Entity extends AbstractUserEntity>
        implements LoginService<Cache, Entity> {

    @Inject
    private RemoteUserOpenFeign remoteUserService;

    @Inject
    private SystemPasswordService passwordService;

    @Inject
    private SystemRecordLogService recordLogService;

    @Inject
    private RedisService<String, String> redisService;

    @Inject
    private SecurityService securityService;

    @Inject
    private IpComponent ipComponent;

    /**
     * 登录
     */
    @SuppressWarnings("unchecked")
    @Override
    public <T extends Cache> T login(String username, String password) {
        // 用户名或密码为空 错误
        if (StringUtils.isAnyBlank(username, password)) {
            recordLogService.recordLoginInfo(username, Constants.LOGIN_FAIL, "用户/密码必须填写");
            throw new ServiceException("用户/密码必须填写");
        }
        // 密码如果不在指定范围内 错误
        if (password.length() < UserConstants.PASSWORD_MIN_LENGTH
                || password.length() > UserConstants.PASSWORD_MAX_LENGTH) {
            recordLogService.recordLoginInfo(username, Constants.LOGIN_FAIL, "用户密码不在指定范围");
            throw new ServiceException("用户密码不在指定范围");
        }
        // 用户名不在指定范围内 错误
        if (username.length() < UserConstants.USERNAME_MIN_LENGTH
                || username.length() > UserConstants.USERNAME_MAX_LENGTH) {
            recordLogService.recordLoginInfo(username, Constants.LOGIN_FAIL, "用户名不在指定范围");
            throw new ServiceException("用户名不在指定范围");
        }
        // IP黑名单校验
        final var blackStr = Convert.toStr(redisService.getCacheObject(CacheConstants.SYS_LOGIN_BLACKIPLIST));
        if (ipComponent.isMatchedIp(blackStr, ipComponent.getIpAddr())) {
            recordLogService.recordLoginInfo(username, Constants.LOGIN_FAIL, "很遗憾，访问IP已被列入系统黑名单");
            throw new ServiceException("很遗憾，访问IP已被列入系统黑名单");
        }

        // 查询用户信息
        final var userResult = remoteUserService.getUserInfo(username, SecurityConstants.INNER);
        System.out.println(userResult.toString());
        if (ObjectUtil.isNull(userResult.getData())) {
            recordLogService.recordLoginInfo(username, Constants.LOGIN_FAIL, "登录用户不存在");
            throw new ServiceException("登录用户：" + username + " 不存在");
        }

        if (HttpStatus.INTERNAL_SERVER_ERROR.value() == userResult.getCode()) {
            throw new ServiceException(userResult.getMsg());
        }

        final var userInfo = userResult.getData();
        final var user = userResult.getData().getUserEntity();
//        if (UserStatus.DELETED.getCode().equals(user.getDelFlag())) {
//            recordLogService.recordLoginInfo(username, Constants.LOGIN_FAIL, "对不起，您的账号已被删除");
//            throw new ServiceException("对不起，您的账号：" + username + " 已被删除");
//        }
        if (UserStatus.DISABLE.getCode().equals(user.getStatus())) {
            recordLogService.recordLoginInfo(username, Constants.LOGIN_FAIL, "用户已停用，请联系管理员");
            throw new ServiceException("对不起，您的账号：" + username + " 已停用");
        }
        passwordService.validate(user, password);
        recordLogService.recordLoginInfo(username, Constants.LOGIN_SUCCESS, "登录成功");
        return (T) userInfo;
    }

    @Override
    public void logout(String loginName) {
        recordLogService.recordLoginInfo(loginName, Constants.LOGOUT, "退出成功");
    }

    /**
     * 注册
     */
    @Override
    public void register(String username, String password) {
        // 用户名或密码为空 错误
        if (StringUtils.isAnyBlank(username, password)) {
            throw new ServiceException("用户/密码必须填写");
        }
        if (username.length() < UserConstants.USERNAME_MIN_LENGTH
                || username.length() > UserConstants.USERNAME_MAX_LENGTH) {
            throw new ServiceException("账户长度必须在2到20个字符之间");
        }
        if (password.length() < UserConstants.PASSWORD_MIN_LENGTH
                || password.length() > UserConstants.PASSWORD_MAX_LENGTH) {
            throw new ServiceException("密码长度必须在5到20个字符之间");
        }

        // 注册用户信息
        final var sysUser = new SysUser();
        sysUser.setUserName(username);
        sysUser.setNickName(username);
        sysUser.setPassword(securityService.encryptPassword(password));
        final var registerResult = remoteUserService.registerUserInfo(sysUser, SecurityConstants.INNER);

        if (HttpStatus.INTERNAL_SERVER_ERROR.value() == registerResult.getCode()) {
            throw new ServiceException(registerResult.getMsg());
        }
        recordLogService.recordLoginInfo(username, Constants.REGISTER, "注册成功");
    }
}
