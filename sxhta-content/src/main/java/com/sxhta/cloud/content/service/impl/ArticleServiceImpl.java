package com.sxhta.cloud.content.service.impl;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sxhta.cloud.common.exception.CommonNullException;
import com.sxhta.cloud.content.entity.Article;
import com.sxhta.cloud.content.mapper.ArticleMapper;
import com.sxhta.cloud.content.request.ArticleRequest;
import com.sxhta.cloud.content.request.ArticleSearchRequest;
import com.sxhta.cloud.content.response.ArticleResponse;
import com.sxhta.cloud.content.service.ArticleService;
import com.sxhta.cloud.remote.domain.SysUser;
import com.sxhta.cloud.remote.model.SystemUserCacheVo;
import com.sxhta.cloud.security.service.TokenService;
import jakarta.inject.Inject;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 平台文章服务实现类
 */
@Service
public class ArticleServiceImpl extends ServiceImpl<ArticleMapper, Article> implements ArticleService {

    @Inject
    private TokenService<SystemUserCacheVo, SysUser> tokenService;

    @Override
    public Boolean create(ArticleRequest request) {
        final var entity = new Article();
        BeanUtils.copyProperties(request, entity);
        entity.setCreateBy(tokenService.getLoginUser().getUsername());
        final var imageList = request.getImages();
        final var images = listToJsonString(imageList);
        entity.setImages(images);
        return save(entity);
    }

    @Override
    public ArticleResponse getInfoByHash(String hash) {
        final var lqw = new LambdaQueryWrapper<Article>();
        lqw.eq(Article::getHash, hash);
        final var entity = getOne(lqw);
        if (ObjectUtil.isNull(entity)) {
            throw new CommonNullException("该文章分类不存在");
        }
        final var response = new ArticleResponse();
        BeanUtils.copyProperties(entity, response);
        return response;
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
        final var hash = request.getHash();
        final var lqw = new LambdaQueryWrapper<Article>();
        lqw.eq(Article::getHash, hash);
        final var entity = getOne(lqw);
        if (ObjectUtil.isNull(entity)) {
            throw new CommonNullException("该文章不存在");
        }
        BeanUtils.copyProperties(request, entity);
        return updateById(entity);
    }

    @Override
    public List<ArticleResponse> getAdminList(ArticleSearchRequest request) {
        final var lqw = new LambdaQueryWrapper<Article>();
        final var searchTitle = request.getTitle();
        if (StrUtil.isNotBlank(searchTitle)) {
            lqw.and(consumer -> consumer.eq(Article::getTitle, searchTitle));
        }
        final var entityList = list(lqw);
        final var responseList = new ArrayList<ArticleResponse>();
        entityList.forEach(entity -> {
            final var response = new ArticleResponse();
            BeanUtils.copyProperties(entity, response);
            responseList.add(response);
        });
        return responseList;
    }
}
