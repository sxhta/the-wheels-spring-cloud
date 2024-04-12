package com.sxhta.cloud.content.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.sxhta.cloud.common.service.ICommonService;
import com.sxhta.cloud.content.entity.ArticleCategory;
import com.sxhta.cloud.content.request.ArticleCategoryRequest;
import com.sxhta.cloud.content.request.ArticleCategorySearchRequest;
import com.sxhta.cloud.content.response.ArticleCategoryResponse;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * 文章分类
 */
public interface ArticleCategoryService extends IService<ArticleCategory>,
        ICommonService<ArticleCategorySearchRequest, ArticleCategoryRequest, ArticleCategoryResponse> {
    List<ArticleCategoryResponse> getArticleCategoryList();

    String uploadFile(MultipartFile file);
}
