package com.wheels.cloud.backend.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wheels.cloud.backend.entity.Article;
import com.wheels.cloud.backend.request.ArticleListDto;
import com.wheels.cloud.backend.vo.ArticleVo;

import java.util.List;

/**
 * 平台文章服务
 */
public interface IArticleService extends IService<Article> {
    Boolean saveArticle(Article article);

    Boolean deleteArticle(String articleCode);

    Boolean updateArticle(Article article);

    List<ArticleVo> selectArticleAll(ArticleListDto articleListDto);

    ArticleVo selectArticleInfo(String articleCode);
}
