package com.wheels.cloud.backend.request;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.wheels.cloud.backend.entity.BaseEntity;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serial;

/**
 * 平台文章web数据
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
public class ArticleDto extends BaseEntity {
    @Serial
    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

    /**
     * 文章ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 文章编号
     */
    private String platformCode;

    /**
     * 文章类别编号
     */
    private String articleTypeCode;

    /**
     * 文章标题
     */
    @NotBlank(message = "文章标题不能为空")
    @Size(max = 50, message = "文章标题不能超过50个字符")
    private String platformTitle;

    /**
     * 文章内容
     */
    @NotBlank(message = "文章内容不能为空")
    @Size(min = 0, max = 2000, message = "文章内容不能超过2000个字符")
    private String platformContent;


}
