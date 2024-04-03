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
@TableName("content_article")
@EqualsAndHashCode(callSuper = true)
@Schema(name = "文章管理", description = "文章管理实体类")
public final class Article extends BaseHashEntity implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(name = "分类 ID")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @Schema(name = "文章标题")
    private String title;

    @Schema(name = "文章描述")
    private String description;

    @Schema(name = "缩略图")
    private String thumb;

    @Schema(name = "文章图片，存为json数组")
    private String images;

    @Schema(name = "富文本内容", description = "包含样式的富文本内容")
    private String content;

    @Schema(name = "创建者")
    private Long createBy;

    @Schema(name = "修改者")
    private Long updateBy;
}