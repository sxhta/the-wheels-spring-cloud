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
import com.sxhta.cloud.remote.service.AttachmentService;
import com.sxhta.cloud.remote.vo.SystemUserCacheVo;
import com.sxhta.cloud.security.service.TokenService;
import jakarta.inject.Inject;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 平台文章服务实现类
 */
@Service
public class ArticleServiceImpl extends ServiceImpl<ArticleMapper, Article>
        implements ArticleService, Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Inject
    private TokenService<SystemUserCacheVo, SysUser> tokenService;

    @Inject
    private AttachmentService attachmentService;

    @Override
    public Boolean create(ArticleRequest request) {
        final var entity = new Article();
        BeanUtils.copyProperties(request, entity);
        entity.setCreateBy(tokenService.getLoginUser().getUsername());
        final var imageList = request.getImages();
        final var resultImageList = new ArrayList<String>();
        imageList.forEach(originImageUrl -> {
            final var resultImageUrl = attachmentService.clearPrefix(originImageUrl);
            resultImageList.add(resultImageUrl);
        });
        final var images = listToJsonString(resultImageList);
        entity.setImages(images);
        final var originThumb = entity.getThumb();
        final var resulThumb = attachmentService.clearPrefix(originThumb);
        entity.setThumb(resulThumb);
        final var originContent = entity.getContent();
        final var resulContent = attachmentService.clearPrefix(originContent);
        entity.setContent(resulContent);
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
        final var originThumb = response.getThumb();
        final var resulThumb = attachmentService.addPrefix(originThumb);
        response.setThumb(resulThumb);
        final var imagesStr = entity.getImages();
        final var imageList = jsonStringToList(imagesStr);
        final var resultImageList = new ArrayList<String>();
        imageList.forEach(originImageUrl -> {
            final var resultImageUrl = attachmentService.addPrefix(originImageUrl);
            resultImageList.add(resultImageUrl);
        });
        response.setImages(resultImageList);
        final var originContent = entity.getContent();
        final var resulContent = attachmentService.addPrefix(originContent);
        response.setContent(resulContent);
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
    public Boolean updateEntity(ArticleRequest request) {
        final var hash = request.getHash();
        final var lqw = new LambdaQueryWrapper<Article>();
        lqw.eq(Article::getHash, hash);
        final var entity = getOne(lqw);
        if (ObjectUtil.isNull(entity)) {
            throw new CommonNullException("该文章不存在");
        }
        BeanUtils.copyProperties(request, entity);
        final var imageList = request.getImages();
        final var resultImageList = new ArrayList<String>();
        imageList.forEach(originImageUrl -> {
            final var resultImageUrl = attachmentService.clearPrefix(originImageUrl);
            resultImageList.add(resultImageUrl);
        });
        final var images = listToJsonString(resultImageList);
        entity.setImages(images);
        final var originThumb = entity.getThumb();
        final var resulThumb = attachmentService.clearPrefix(originThumb);
        entity.setThumb(resulThumb);
        final var originContent = entity.getContent();
        final var resulContent = attachmentService.clearPrefix(originContent);
        entity.setContent(resulContent);
        return updateById(entity);
    }

    @Override
    public List<ArticleResponse> getAdminList(ArticleSearchRequest request) {
        final var lqw = new LambdaQueryWrapper<Article>();
        lqw.isNull(Article::getDeleteTime)
                .orderByDesc(Article::getCreateTime);
        final var searchTitle = request.getTitle();
        if (StrUtil.isNotBlank(searchTitle)) {
            lqw.and(consumer -> consumer.eq(Article::getTitle, searchTitle));
        }
        final var entityList = list(lqw);
        final var responseList = new ArrayList<ArticleResponse>();
        entityList.forEach(entity -> {
            final var response = new ArticleResponse();
            BeanUtils.copyProperties(entity, response);
            final var originThumb = response.getThumb();
            final var resulThumb = attachmentService.addPrefix(originThumb);
            response.setThumb(resulThumb);
            responseList.add(response);
        });
        return responseList;
    }
}
