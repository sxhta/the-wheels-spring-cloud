package com.sxhta.cloud.security.feign;

import cn.hutool.core.util.ObjectUtil;
import com.sxhta.cloud.common.component.IpComponent;
import com.sxhta.cloud.common.component.ServletComponent;
import com.sxhta.cloud.common.constant.SecurityConstants;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import jakarta.inject.Inject;
import org.springframework.stereotype.Component;

import java.io.Serial;
import java.io.Serializable;

/**
 * feign 请求拦截器
 */
@Component
public class FeignRequestInterceptor implements RequestInterceptor, Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private static final String FEIGN_HEADER = "X-Forwarded-For";

    @Inject
    private ServletComponent servletComponent;

    @Inject
    private IpComponent ipComponent;

    @Override
    public void apply(RequestTemplate requestTemplate) {
        final var httpServletRequest = servletComponent.getRequest();
        if (ObjectUtil.isNotNull(httpServletRequest)) {
            final var headers = servletComponent.getHeaders(httpServletRequest);
            // 传递用户信息请求头，防止丢失
            final var userId = headers.get(SecurityConstants.DETAILS_USER_ID);
            if (ObjectUtil.isNotEmpty(userId)) {
                requestTemplate.header(SecurityConstants.DETAILS_USER_ID, userId);
            }
            final var userKey = headers.get(SecurityConstants.USER_KEY);
            if (ObjectUtil.isNotEmpty(userKey)) {
                requestTemplate.header(SecurityConstants.USER_KEY, userKey);
            }
            final var userName = headers.get(SecurityConstants.DETAILS_USERNAME);
            if (ObjectUtil.isNotEmpty(userName)) {
                requestTemplate.header(SecurityConstants.DETAILS_USERNAME, userName);
            }
            final var authentication = headers.get(SecurityConstants.AUTHORIZATION_HEADER);
            if (ObjectUtil.isNotEmpty(authentication)) {
                requestTemplate.header(SecurityConstants.AUTHORIZATION_HEADER, authentication);
            }

            // 配置客户端IP
            requestTemplate.header(FEIGN_HEADER, ipComponent.getIpAddr());
        }
    }
}