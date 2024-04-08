package com.sxhta.cloud.common.net.impl;

import com.sxhta.cloud.common.exception.CommonException;
import com.sxhta.cloud.common.net.IRequestCallback;
import com.sxhta.cloud.common.net.OkhttpService;
import jakarta.annotation.PostConstruct;
import jakarta.inject.Singleton;
import okhttp3.*;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.Serial;
import java.io.Serializable;
import java.util.Map;

@Singleton
@Service
public class OkhttpServiceImpl implements OkhttpService, Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private static final Logger logger = LoggerFactory.getLogger(OkhttpServiceImpl.class);

    private OkHttpClient okHttpClient;

    @PostConstruct
    private void init() {
        okHttpClient = new OkHttpClient();
    }


    @Override
    public <Value> Request defaultGetRequest(@NotNull String url, Map<String, Value> params) {
        final var requestBuilder = new Request.Builder();
        final var originUrl = HttpUrl.parse(url);
        if (originUrl == null) {
            throw new CommonException("URL 地址转换失败");
        }
        if (params == null || params.isEmpty()) {
            requestBuilder.url(originUrl);
        } else {
            final var httpUrlBuilder = originUrl.newBuilder();
            params.forEach((key, value) -> {
                httpUrlBuilder.addQueryParameter(key, String.valueOf(value));
                final var fullParamsUrl = httpUrlBuilder.build();
                requestBuilder.url(fullParamsUrl);
            });
        }
        return requestBuilder
                .get()
                .build();
    }

    @Override
    public <Value> Response getSync(@NotNull String url, Map<String, Value> params) {
        final var request = defaultGetRequest(url, params);
        final var call = okHttpClient.newCall(request);
        try {
            return call.execute();
        } catch (IOException e) {
            throw new CommonException(e.getMessage());
        }
    }

    @Override
    public <Value> String getSyncWithBodyString(@NotNull String url, Map<String, Value> params) {
        final var response = getSync(url, params);
        final var body = response.body();
        if (body == null) {
            throw new NullPointerException("响应体为空");
        }
        try {
            return body.string();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public <Value> void getAsync(@NotNull String url, Map<String, Value> params, @NotNull Callback callback) {
        final var request = defaultGetRequest(url, params);
        final var call = okHttpClient.newCall(request);
        call.enqueue(callback);
    }

    @Override
    public <Value> void getAsyncWithBodyString(@NotNull String url, Map<String, Value> params, @NotNull IRequestCallback callback) {
        final var request = defaultGetRequest(url, params);
        final var call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                logger.error(call.toString());
                logger.error(e.getMessage());
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                final var body = response.body();
                if (body != null) {
                    final var bodyString = body.string();
                    callback.onResponseString(bodyString);
                }
            }
        });
    }
}
