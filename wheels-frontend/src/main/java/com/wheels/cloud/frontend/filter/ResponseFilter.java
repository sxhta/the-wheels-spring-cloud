package com.wheels.cloud.frontend.filter;


import com.sxhta.cloud.common.utils.RequestUtil;
import jakarta.inject.Inject;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.Serial;
import java.io.Serializable;
import java.nio.charset.StandardCharsets;


/**
 * 返回值输出过滤器
 */
@Component
public final class ResponseFilter implements Filter, Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Inject
    private ResponseRouter responseRouter;

    @SuppressWarnings("CallToPrintStackTrace")
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain)
            throws IOException, ServletException {
        //转换成代理类
        final var wrapperResponse = new ResponseWrapper((HttpServletResponse) response);

        // 这里只拦截返回，直接让请求过去，如果在请求前有处理，可以在这里处理
        filterChain.doFilter(request, wrapperResponse);
        //获取返回值
        final var content = wrapperResponse.getContent();
        //判断是否有值
        if (content.length > 0) {
            String str = new String(content, StandardCharsets.UTF_8);

            try {
                final var req = (HttpServletRequest) request;
                str = responseRouter.filter(str, RequestUtil.getUri(req));
            } catch (Exception e) {
                e.printStackTrace();
            }
            //把返回值输出到客户端
            final var outputStream = response.getOutputStream();
            if (!str.isEmpty()) {
                outputStream.write(str.getBytes());
                outputStream.flush();
                outputStream.close();
                //输出到客户端
                response.flushBuffer();
            }
        }
    }
}
