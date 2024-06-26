package com.sxhta.cloud.content.service.impl;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sxhta.cloud.common.exception.CommonNullException;
import com.sxhta.cloud.content.entity.ArticleCategory;
import com.sxhta.cloud.content.mapper.ArticleCategoryMapper;
import com.sxhta.cloud.content.request.ArticleCategoryRequest;
import com.sxhta.cloud.content.request.ArticleCategorySearchRequest;
import com.sxhta.cloud.content.response.ArticleCategoryResponse;
import com.sxhta.cloud.content.service.ArticleCategoryService;
import com.sxhta.cloud.remote.domain.SysUser;
import com.sxhta.cloud.remote.service.AttachmentService;
import com.sxhta.cloud.remote.vo.SystemUserCacheVo;
import com.sxhta.cloud.security.service.TokenService;
import jakarta.inject.Inject;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class ArticleCategoryServiceImpl
        extends ServiceImpl<ArticleCategoryMapper, ArticleCategory>
        implements ArticleCategoryService, Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Inject
    private TokenService<SystemUserCacheVo, SysUser> tokenService;

    @Inject
    private AttachmentService attachmentService;

    @Override
    public Boolean create(ArticleCategoryRequest request) {
        final var entity = new ArticleCategory();
        BeanUtils.copyProperties(request, entity);
        final var loginUser = tokenService.getLoginUser();
        final var createBy = loginUser.getUsername();
        entity.setCreateBy(createBy);
        final var originUrl = entity.getThumb();
        final var resultUrl = attachmentService.clearPrefix(originUrl);
        entity.setThumb(resultUrl);
        return save(entity);
    }

    @Override
    public ArticleCategoryResponse getInfoByHash(String hash) {
        final var lqw = new LambdaQueryWrapper<ArticleCategory>();
        lqw.eq(ArticleCategory::getHash, hash);
        final var entity = getOne(lqw);
        if (ObjectUtil.isNull(entity)) {
            throw new CommonNullException("该文章分类不存在");
        }
        final var response = new ArticleCategoryResponse();
        BeanUtils.copyProperties(entity, response);
        return response;
    }

    @Override
    public Boolean softDeleteByHash(String hash) {
        final var lqw = new LambdaQueryWrapper<ArticleCategory>();
        lqw.eq(ArticleCategory::getHash, hash);
        final var entity = getOne(lqw);
        if (ObjectUtil.isNull(entity)) {
            throw new CommonNullException("该文章分类不存在");
        }
        entity.setDeleteTime(LocalDateTime.now());
        return updateById(entity);
    }

    @Override
    public Boolean deleteByHash(String hash) {
        final var lqw = new LambdaQueryWrapper<ArticleCategory>();
        lqw.eq(ArticleCategory::getHash, hash);
        final var entity = getOne(lqw);
        if (ObjectUtil.isNull(entity)) {
            throw new CommonNullException("该文章分类不存在");
        }
        return removeById(entity);
    }

    @Override
    public Boolean updateEntity(ArticleCategoryRequest request) {
        final var hash = request.getHash();
        final var lqw = new LambdaQueryWrapper<ArticleCategory>();
        lqw.eq(ArticleCategory::getHash, hash);
        final var entity = getOne(lqw);
        if (ObjectUtil.isNull(entity)) {
            throw new CommonNullException("该文章分类不存在");
        }
        BeanUtils.copyProperties(request, entity);
        return updateById(entity);
    }

    @Override
    public List<ArticleCategoryResponse> getAdminList(ArticleCategorySearchRequest request) {
        final var lqw = new LambdaQueryWrapper<ArticleCategory>();
        lqw.isNull(ArticleCategory::getDeleteTime)
                .orderByDesc(ArticleCategory::getCreateTime);
        final var searchTitle = request.getTitle();
        if (StrUtil.isNotBlank(searchTitle)) {
            lqw.and(consumer -> consumer.eq(ArticleCategory::getTitle, searchTitle));
        }
        final var entityList = list(lqw);
        final var responseList = new ArrayList<ArticleCategoryResponse>();
        entityList.forEach(entity -> {
            final var response = new ArticleCategoryResponse();
            BeanUtils.copyProperties(entity, response);
            final var originUrl = response.getThumb();
            final var resultUrl = attachmentService.addPrefix(originUrl);
            response.setThumb(resultUrl);
            responseList.add(response);
        });
        return responseList;
    }

    @Override
    public List<ArticleCategoryResponse> getArticleCategoryList() {
        final var lqw = new LambdaQueryWrapper<ArticleCategory>();
        lqw.isNull(ArticleCategory::getDeleteTime)
                .select(ArticleCategory::getId)
                .select(ArticleCategory::getTitle)
                .select(ArticleCategory::getHash);
        final var entityList = list(lqw);
        final var responseList = new ArrayList<ArticleCategoryResponse>();
        entityList.forEach(entity -> {
            final var response = new ArticleCategoryResponse();
            BeanUtils.copyProperties(entity, response);
            responseList.add(response);
        });
        return responseList;
    }

}