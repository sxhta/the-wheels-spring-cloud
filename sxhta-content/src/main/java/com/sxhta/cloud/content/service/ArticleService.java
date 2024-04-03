package com.sxhta.cloud.content.service;

import com.wheels.cloud.backend.response.ArticleDto;

/**
 * 平台文章服务
 */
public interface ArticleService {
    Boolean create(ArticleDto articleDto);

    Boolean delete(String articleCode);

    Boolean updateArticle(ArticleDto articleDto);

    Object getAdminList();
}
