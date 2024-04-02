package com.sxhta.cloud.auth.service.impl;


import com.sxhta.cloud.auth.service.SystemRecordLogService;
import com.sxhta.cloud.common.component.IpComponent;
import com.sxhta.cloud.common.constant.Constants;
import com.sxhta.cloud.common.constant.SecurityConstants;
import com.sxhta.cloud.remote.RemoteLogOpenFeign;
import com.sxhta.cloud.remote.domain.SysLogininfor;
import jakarta.inject.Inject;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

/**
 * 记录日志方法
 */
@Service
public class SystemRecordLogServiceImpl implements SystemRecordLogService {

    @Inject
    private RemoteLogOpenFeign remoteLogService;

    @Inject
    private IpComponent ipComponent;

    /**
     * 记录登录信息
     *
     * @param username 用户名
     * @param status   状态
     * @param message  消息内容
     */
    @Override
    public void recordLoginInfo(String username, String status, String message) {
        final var loginInfo = new SysLogininfor();
        loginInfo.setUserName(username);
        loginInfo.setIpaddr(ipComponent.getIpAddr());
        loginInfo.setMsg(message);
        // 日志状态
        if (StringUtils.equalsAny(status, Constants.LOGIN_SUCCESS, Constants.LOGOUT, Constants.REGISTER)) {
            loginInfo.setStatus(Constants.LOGIN_SUCCESS_STATUS);
        } else if (Constants.LOGIN_FAIL.equals(status)) {
            loginInfo.setStatus(Constants.LOGIN_FAIL_STATUS);
        }
        remoteLogService.saveLogininfor(loginInfo, SecurityConstants.INNER);
    }
}
