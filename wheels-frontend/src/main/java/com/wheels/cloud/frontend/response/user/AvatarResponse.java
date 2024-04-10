package com.wheels.cloud.frontend.response.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serial;
import java.io.Serializable;

@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@Schema(name = "头像上传", description = "头像上传响应体")
public final class AvatarResponse implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(name = "头像文件名")
    private String name;

    @Schema(name = "头像路径")
    @JsonProperty("image_url")
    private String imageUrl;
}
