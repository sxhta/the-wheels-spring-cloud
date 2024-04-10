package com.sxhta.cloud.remote.filter;


import com.sxhta.cloud.common.component.RequestComponent;
import com.sxhta.cloud.common.exception.CommonException;
import jakarta.inject.Inject;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import okio.Okio;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.Serial;
import java.io.Serializable;
import java.nio.charset.StandardCharsets;


/**
 * 返回值输出过滤器
 */
@Component
public final class ResponseFilter extends HttpFilter implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Inject
    private ResponseRouter responseRouter;

    @Inject
    private RequestComponent requestComponent;

    @Override
    protected void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        //转换成代理类
        final var wrapperResponse = new ResponseWrapper(response);
        // 这里只拦截返回，直接让请求过去，如果在请求前有处理，可以在这里处理
        super.doFilter(request, wrapperResponse, chain);

        //获取返回值
        final var content = wrapperResponse.getContent();
        //判断是否有值
        if (content.length > 0) {
            var str = new String(content, StandardCharsets.UTF_8);

            try {
                str = responseRouter.filter(str, requestComponent.getUri(request));
            } catch (Exception e) {
                //noinspection CallToPrintStackTrace
                e.printStackTrace();
                throw new CommonException("响应拦截错误");
            }
            //把返回值输出到客户端
            final var outputStream = response.getOutputStream();
            final var sink = Okio.sink(outputStream);
            final var buffer = Okio.buffer(sink);

            if (!str.isEmpty()) {
                buffer.write(str.getBytes());
                buffer.flush();
                buffer.close();
                response.flushBuffer();
            }
        }
    }
}
