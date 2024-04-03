package com.wheels.cloud.backend.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serial;


/**
 * 平台文章类型实体类
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@TableName("wheels_article_type")
public class ArticleType extends BaseEntity {
    @Serial
    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

    /**
     * 文章类型ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 文章类型编号
     */
    private String typeCode;

    /**
     * 文章类别名称
     */
    @NotBlank(message = "文章类别名称不能为空")
    @Size(max = 50, message = "文章类别名称不能超过50个字符")
    private String typeName;

    /**
     * 类别状态 0-禁用 1-启用
     */
    private Boolean typeStatus = false;


}
