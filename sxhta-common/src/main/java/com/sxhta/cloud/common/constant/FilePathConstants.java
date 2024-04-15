package com.sxhta.cloud.common.constant;

import java.io.Serial;
import java.io.Serializable;

/**
 * 文件上传路径
 */
public final class FilePathConstants implements Serializable {


    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 文章图片路径
     */
    public static final String ARTICLE_IMAGE_PATH = "/article/image";

    public static final String ARTICLE_THUMB_PATH = "/article/thumb";

    public static final String ARTICLE_CONTENT_PATH = "/article/content";

    public static final String ARTICLE_CATEGORY_THUMB_PATH = "/article/category/thumb";
}
