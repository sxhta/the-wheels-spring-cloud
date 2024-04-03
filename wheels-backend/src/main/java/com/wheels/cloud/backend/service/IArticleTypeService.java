package com.wheels.cloud.backend.service;

import com.wheels.cloud.backend.entity.ArticleType;
import com.wheels.cloud.backend.vo.ArticleTypeVo;

import java.util.List;

public interface IArticleTypeService {
    Boolean saveArticleType(ArticleType articleType);

    Boolean deleteArticleType(String articleTypeCode);

    Boolean updateArticleType(ArticleType articleType);

    List<ArticleTypeVo> selectArticleTypeAll();

    ArticleTypeVo selectArticleTypeInfo(String articleTypeCode);
}
