package com.sxhta.cloud.content.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sxhta.cloud.content.entity.Article;
import com.sxhta.cloud.content.entity.ArticleCategory;
import com.sxhta.cloud.content.mapper.ArticleMapper;
import com.sxhta.cloud.content.request.ArticleRequest;
import com.sxhta.cloud.content.request.ArticleSearchRequest;
import com.sxhta.cloud.content.response.ArticleResponse;
import com.sxhta.cloud.content.service.ArticleService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 平台文章服务实现类
 */
@Service
public class ArticleServiceImpl extends ServiceImpl<ArticleMapper, Article> implements ArticleService {

    @Override
    public Boolean create(ArticleRequest request) {
        final var entity = new Article();
        BeanUtils.copyProperties(request, entity);
        return save(entity);
    }

    @Override
    public ArticleResponse getInfoByHash(String hash) {
        return null;
    }

    @Override
    public Boolean softDeleteByHash(String hash) {
        return null;
    }

    @Override
    public Boolean deleteByHash(String hash) {
        return null;
    }

    @Override
    public Boolean updateCategory(ArticleRequest request) {
        return null;
    }

    @Override
    public List<ArticleResponse> getAdminList(ArticleSearchRequest request) {
        return null;
    }
}
