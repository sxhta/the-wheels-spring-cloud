package com.wheels.cloud.backend.service;

import com.wheels.cloud.backend.response.ArticleDto;

public interface IArticleTypeService {
    Boolean saveArticleType(ArticleDto articleDto);

    Boolean deleteArticleType(String articleTypeCode);

    Boolean updateArticleType(ArticleDto articleDto);

    Object selectArticleTypeAll();

    Object selectArticleTypeInfo();
}
