package com.wheels.cloud.backend.request;

import com.baomidou.mybatisplus.annotation.TableField;
import com.wheels.cloud.backend.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serial;
import java.time.LocalDateTime;

/**
 * 平台文章web数据
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
public class ArticleListDto extends BaseEntity {
    @Serial
    @TableField(exist = false)
    private static final long serialVersionUID = 1L;


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
    private String platformTitle;

    /**
     * 文章内容
     */
    private String platformContent;

    /**
     * 发布起始时间
     */
    private LocalDateTime startTime;

    /**
     * 发布结束时间
     */
    private LocalDateTime endTime;


}
