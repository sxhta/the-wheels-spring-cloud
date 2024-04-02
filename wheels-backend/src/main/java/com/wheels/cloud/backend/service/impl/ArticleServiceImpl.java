package com.wheels.cloud.backend.service.impl;

import com.wheels.cloud.backend.dto.ArticleDto;
import com.wheels.cloud.backend.service.IArticleService;
import org.springframework.stereotype.Service;

/**
 * 平台文章服务实现类
 */
@Service
public class ArticleServiceImpl implements IArticleService {
    @Override
    public Boolean saveArticle(ArticleDto articleDto) {
        return null;
    }

    @Override
    public Boolean deleteArticle(String articleCode) {
        return null;
    }

    @Override
    public Boolean updateArticle(ArticleDto articleDto) {
        return null;
    }

    @Override
    public Object selectArticleAll() {
        return null;
    }

    @Override
    public Object selectArticleInfo() {
        return null;
    }
}
