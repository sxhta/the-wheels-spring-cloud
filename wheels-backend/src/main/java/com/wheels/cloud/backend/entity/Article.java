package com.wheels.cloud.backend.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serial;
import java.time.LocalDateTime;


/**
 * 平台文章实体类
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@TableName("wheels_platform_article")
public class Article extends BaseEntity {
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
     * 文章类别编号
     */
    @NotBlank(message = "文章标题不能为空")
    @Size(max = 50, message = "文章标题不能超过50个字符")
    private String platformTitle;

    /**
     * 文章类别编号
     */
    @NotBlank(message = "文章内容不能为空")
    @Size(max = 2000, message = "文章内容不能超过2000个字符")
    private String platformContent;

    /**
     * 发布人
     */
    private Long publisher;

    /**
     * 发布时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime publishTime;

}
