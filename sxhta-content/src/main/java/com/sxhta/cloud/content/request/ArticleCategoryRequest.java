package com.sxhta.cloud.content.request;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.sxhta.cloud.common.domain.BaseHashEntity;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serial;
import java.io.Serializable;

@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@Schema(name = "文章分类管理", description = "文章分类管理实体类")
public final class ArticleCategoryRequest extends BaseHashEntity implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(name = "分类标题")
    private String title;

    @Parameter(name = "分类描述")
    private String description;

    @Schema(name = "缩略图")
    private String thumb;

    @Schema(name = "父级分类ID")
    private Long parentCategoryId = 0L;

    @Schema(name = "当前第几级")
    private Integer level = 0;
}