package com.sxhta.cloud.wheels.backend.response.complain;

import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serial;
import java.io.Serializable;

@Data
@Accessors(chain = true)
@Schema(name = "投诉类型", description = "投诉类型响应体")
public class ComplainTypeToInfoResponse implements Serializable {
    @Serial
    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
    /**
     * id
     */
    private Long id;
    /**
     * hash
     */
    private String hash;
    /**
     * 类型名称
     */
    private String name;
}
