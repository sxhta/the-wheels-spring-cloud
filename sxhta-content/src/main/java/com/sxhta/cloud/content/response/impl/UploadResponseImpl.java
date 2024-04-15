package com.sxhta.cloud.content.response.impl;


import com.sxhta.cloud.content.response.UploadResponse;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.inject.Singleton;
import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.stereotype.Component;

@Data
@Singleton
@Component
@ApiResponses
@Accessors(chain = true)
@Schema(name = "上传返回值", description = "上传返回值")
public final class UploadResponseImpl implements UploadResponse {

    @Schema(name = "文件url")
    private String url;

    @Schema(name = "文件名")
    private String name;
}
