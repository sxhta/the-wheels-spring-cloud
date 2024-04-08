package com.sxhta.cloud.common.net;

import jakarta.annotation.Nonnull;
import jakarta.validation.constraints.NotNull;
import okhttp3.Callback;
import okhttp3.Request;
import okhttp3.Response;

import java.util.Map;

public interface OkhttpService {

    <Value> Request defaultGetRequest(@NotNull String url, Map<String, Value> params);

    <Value> Response getSync(@Nonnull String url, Map<String, Value> params);

    <Value> String getSyncWithBodyString(@Nonnull String url, Map<String, Value> params);

    <Value> void getAsync(@Nonnull String url, Map<String, Value> params, @Nonnull Callback callback);

    <Value> void getAsyncWithBodyString(@Nonnull String url, Map<String, Value> params, @Nonnull IRequestCallback callback);
}
