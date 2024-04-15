package com.sxhta.cloud.content.response;

public interface UploadResponse {

    String getUrl();

    String getName();

    UploadResponse setUrl(String url);

    UploadResponse setName(String name);
}
