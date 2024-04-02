package com.wheels.cloud.backend.service;

import com.wheels.cloud.backend.dto.ArticleDto;

/**
 * 平台文章服务
 */
public interface IArticleService {
    Boolean saveArticle(ArticleDto articleDto);

    Boolean deleteArticle(String articleCode);

    Boolean updateArticle(ArticleDto articleDto);

    Object selectArticleAll();

    Object selectArticleInfo();
}
