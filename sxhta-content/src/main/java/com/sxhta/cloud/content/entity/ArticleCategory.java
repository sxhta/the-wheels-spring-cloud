package com.sxhta.cloud.content.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.sxhta.cloud.common.domain.BaseHashEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serial;
import java.io.Serializable;

@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@TableName("content_article_category")
@Schema(name = "文章分类管理", description = "文章分类管理实体类")
public final class ArticleCategory extends BaseHashEntity implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(name = "分类 ID")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @Schema(name = "分类标题")
    private String title;

    @Schema(name = "分类描述")
    private String description;

    @Schema(name = "缩略图")
    private String thumb;

    @Schema(name = "父级分类ID")
    private String parentHash;

    @Schema(name = "当前第几级")
    private Integer level = 0;

    @Schema(name = "创建者")
    private String createBy;

    @Schema(name = "修改者")
    private String updateBy;
}