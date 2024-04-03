package com.sxhta.cloud.wheels.auth.service.owner.impl;


import com.sxhta.cloud.common.component.IpComponent;
import com.sxhta.cloud.common.constant.Constants;
import com.sxhta.cloud.common.constant.SecurityConstants;
import com.sxhta.cloud.wheels.auth.service.owner.FrontOwnerRecordLogService;
import com.sxhta.cloud.wheels.remote.openfeign.owner.FrontOwnerLogOpenFeign;
import com.sxhta.cloud.wheels.remote.domain.owner.WheelsFrontOwnerLoginInfo;
import jakarta.inject.Inject;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

/**
 * 记录日志方法
 */
@Service
public class FrontOwnerRecordLogServiceImpl implements FrontOwnerRecordLogService {

    @Inject
    private FrontOwnerLogOpenFeign frontOwnerLogOpenFeign;

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
        final var loginInfo = new WheelsFrontOwnerLoginInfo();
        loginInfo.setUserName(username);
        loginInfo.setIpaddr(ipComponent.getIpAddr());
        loginInfo.setMsg(message);
        // 日志状态
        if (StringUtils.equalsAny(status, Constants.LOGIN_SUCCESS, Constants.LOGOUT, Constants.REGISTER)) {
            loginInfo.setStatus(Constants.LOGIN_SUCCESS_STATUS);
        } else if (Constants.LOGIN_FAIL.equals(status)) {
            loginInfo.setStatus(Constants.LOGIN_FAIL_STATUS);
        }
        frontOwnerLogOpenFeign.saveLoginInfo(loginInfo, SecurityConstants.INNER);
    }
}
