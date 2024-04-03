package com.wheels.cloud.backend.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sxhta.cloud.common.exception.ServiceException;
import com.wheels.cloud.backend.entity.Article;
import com.wheels.cloud.backend.mapper.ArticleMapper;
import com.wheels.cloud.backend.request.ArticleListDto;
import com.wheels.cloud.backend.service.IArticleService;
import com.wheels.cloud.backend.vo.ArticleVo;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 平台文章服务实现类
 */
@Service
public class ArticleServiceImpl extends ServiceImpl<ArticleMapper, Article> implements IArticleService {

    /**
     * 新增平台文章
     *
     * @param article 文章实体
     * @return true新增成功 false新增失败
     */
    @Override
    public Boolean saveArticle(Article article) {
        checkArticleTitleIsExist(article);
        return save(article);
    }

    /**
     * 删除平台文章
     *
     * @param articleCode 文章编号
     * @return true删除成功 false删除失败
     */

    @Override
    public Boolean deleteArticle(String articleCode) {
        final var articleLqw = new LambdaQueryWrapper<Article>();
        articleLqw.eq(Article::getPlatformCode, articleCode);
        final var article = getOne(articleLqw);
        if (ObjectUtil.isNull(article)) {
            throw new ServiceException("该文章数据异常，请联系管理员");
        }
        return removeById(article.getId());
    }

    /**
     * 修改平台文章
     *
     * @param article 文章实体
     * @return true修改成功 false修改失败
     */
    @Override
    public Boolean updateArticle(Article article) {
        checkArticleTitleIsExist(article);
        return updateById(article);
    }

    /**
     * 查询平台文章列表
     *
     * @param articleListDto 模糊查询参数
     * @return 平台文章列表
     */
    @Override
    public List<ArticleVo> selectArticleAll(ArticleListDto articleListDto) {
        final var articleVoList = new ArrayList<ArticleVo>();
        final var articleLqw = new LambdaQueryWrapper<Article>();
        if (StrUtil.isNotBlank(articleListDto.getPlatformTitle())) {
            articleLqw.like(Article::getPlatformTitle, articleListDto.getPlatformTitle());
        }
        if (StrUtil.isNotBlank(articleListDto.getPlatformContent())) {
            articleLqw.like(Article::getPlatformContent, articleListDto.getPlatformContent());
        }
        if (ObjectUtil.isNotNull(articleListDto.getStartTime()) && ObjectUtil.isNotNull(articleListDto.getEndTime())) {
            articleLqw.between(Article::getPublishTime, articleListDto.getStartTime(), articleListDto.getEndTime());
        }
        final var articleList = list(articleLqw);
        if (CollUtil.isNotEmpty(articleList)) {
            articleList.forEach(article -> {
                final var articleVo = new ArticleVo();
                BeanUtils.copyProperties(article, articleVo);
                //查询发布人信息
                //TODO：通过人员ID查询发布人信息
//                articleVo.setPublisher();
                articleVoList.add(articleVo);
            });
        }
        return articleVoList;
    }

    /**
     * 查询平台文章详情
     *
     * @param articleCode 文章编号
     * @return 平台文章详情
     */
    @Override
    public ArticleVo selectArticleInfo(String articleCode) {
        final var articleVo = new ArticleVo();
        final var articleLqw = new LambdaQueryWrapper<Article>();
        articleLqw.eq(Article::getPlatformCode, articleCode);
        final var article = getOne(articleLqw);
        if (ObjectUtil.isNotNull(article)) {
            BeanUtils.copyProperties(article, articleVo);
        }
        return articleVo;
    }

    private void checkArticleTitleIsExist(Article article) {
        final var articleLqw = new LambdaQueryWrapper<Article>();
        articleLqw.eq(Article::getPlatformTitle, article.getPlatformTitle());
        final var articleByTitle = getOne(articleLqw);
        if (ObjectUtil.isNotNull(articleByTitle)) {
            throw new ServiceException("该标题已存在");
        }
    }
}
