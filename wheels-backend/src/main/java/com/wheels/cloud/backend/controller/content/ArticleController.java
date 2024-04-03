package com.wheels.cloud.backend.controller.content;


import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.sxhta.cloud.common.exception.ServiceException;
import com.sxhta.cloud.common.web.domain.CommonResponse;
import com.wheels.cloud.backend.entity.Article;
import com.wheels.cloud.backend.entity.ArticleType;
import com.wheels.cloud.backend.request.ArticleDto;
import com.wheels.cloud.backend.request.ArticleListDto;
import com.wheels.cloud.backend.request.ArticleTypeDto;
import com.wheels.cloud.backend.service.IArticleService;
import com.wheels.cloud.backend.service.IArticleTypeService;
import com.wheels.cloud.backend.vo.ArticleTypeVo;
import com.wheels.cloud.backend.vo.ArticleVo;
import jakarta.inject.Inject;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import static com.sxhta.cloud.common.web.domain.CommonResponse.error;
import static com.sxhta.cloud.common.web.domain.CommonResponse.success;

/**
 * 平台文章
 */
@RestController
@RequestMapping("/article")
public class ArticleController {
    @Inject
    private IArticleService articleService;

    @Inject
    private IArticleTypeService articleTypeService;


    /**
     * 平台文章新增
     *
     * @param articleDto 平台文章web数据
     * @return true新增成功 false新增失败
     */
    @PostMapping("/save")
    public CommonResponse<String> saveArticle(@RequestBody ArticleDto articleDto) {
        if (StrUtil.isBlank(articleDto.getArticleTypeCode())) {
            throw new ServiceException("文章类型异常");
        }
        if (StrUtil.isBlank(articleDto.getPlatformTitle())) {
            throw new ServiceException("文章标题不能为空");
        }
        if (StrUtil.isBlank(articleDto.getPlatformContent())) {
            throw new ServiceException("文章内容不能为空");
        }
        if (ObjectUtil.isNull(articleDto.getPlatformContent())) {
            throw new ServiceException("发布人异常");
        }
        final var article = new Article();
        BeanUtils.copyProperties(articleDto, article);
        if (articleService.saveArticle(article)) {
            return success("新增成功");
        }
        return error("新增失败");
    }

    /**
     * 平台文章删除
     *
     * @param articleCode 当前这一条数据的编号
     * @return true删除成功 false删除失败
     */
    @DeleteMapping("/delete")
    public CommonResponse<String> deleteArticle(@RequestParam String articleCode) {
        //TODO:判断articleCode是否为null
        if (articleService.deleteArticle(articleCode)) {
            return success("删除成功");
        }
        return error("删除失败");
    }

    /**
     * 平台文章修改
     *
     * @param articleDto 平台文章web数据
     * @return true修改成功 false修改失败
     */
    @PutMapping("/update")
    public CommonResponse<String> updateArticle(@RequestBody ArticleDto articleDto) {
        if (StrUtil.isBlank(articleDto.getArticleTypeCode())) {
            throw new ServiceException("文章类型异常");
        }
        if (StrUtil.isBlank(articleDto.getPlatformTitle())) {
            throw new ServiceException("文章标题不能为空");
        }
        if (StrUtil.isBlank(articleDto.getPlatformContent())) {
            throw new ServiceException("文章内容不能为空");
        }
        if (ObjectUtil.isNull(articleDto.getPlatformContent())) {
            throw new ServiceException("发布人异常");
        }
        if (ObjectUtil.isNull(articleDto.getId())) {
            throw new ServiceException("该文章数据异常，请联系管理员");
        }
        final var article = new Article();
        BeanUtils.copyProperties(articleDto, article);
        if (articleService.updateArticle(article)) {
            return success("修改成功");
        }
        return error("修改失败");
    }

    /**
     * 查询平台文章列表
     *
     * @param articleListDto 模糊查询参数
     * @return 文章列表
     */
    @GetMapping("/list")
    public CommonResponse selectArticleAll(@ModelAttribute("ArticleListDto") ArticleListDto articleListDto) {
        return success("查询成功", articleService.selectArticleAll(articleListDto));
    }

    /**
     * 查询平台文章详情
     *
     * @param articleCode 文章编号
     * @return 文章详情
     */
    @GetMapping("/info")
    public CommonResponse<ArticleVo> selectArticleInfo(@RequestParam String articleCode) {
        return success("查询成功", articleService.selectArticleInfo(articleCode));
    }

    /**
     * 文章类型新增
     *
     * @param articleTypeDto 文章类型web数据
     * @return true新增成功 false新增失败
     */
    @PostMapping("/save/type")
    public CommonResponse<String> saveArticleType(@RequestBody ArticleTypeDto articleTypeDto) {
        if (StrUtil.isBlank(articleTypeDto.getTypeName())) {
            throw new ServiceException("文章类别名称不能为空");
        }
        final var articleType = new ArticleType();
        BeanUtils.copyProperties(articleTypeDto, articleType);
        if (articleTypeService.saveArticleType(articleType)) {
            return success("新增成功");
        }
        return error("新增失败");
    }

    /**
     * 文章类型删除
     *
     * @param articleTypeCode 当前这一条文章类型的编号
     * @return true删除成功 false删除失败
     */
    @DeleteMapping("/delete/type")
    public CommonResponse<String> deleteArticleType(@RequestParam String articleTypeCode) {
        if (StrUtil.isBlank(articleTypeCode)) {
            throw new ServiceException("该数据异常，请联系管理员");
        }
        if (articleTypeService.deleteArticleType(articleTypeCode)) {
            return success("删除成功");
        }
        return error("删除失败");
    }

    /**
     * 文章类型修改
     *
     * @param articleTypeDto 文章类型web数据
     * @return true修改成功 false修改失败
     */
    @PutMapping("/update/type")
    public CommonResponse<String> updateArticleType(@RequestBody ArticleTypeDto articleTypeDto) {
        if (StrUtil.isBlank(articleTypeDto.getTypeName())) {
            throw new ServiceException("文章类型名称不能为空");
        }
        final var articleType = new ArticleType();
        BeanUtils.copyProperties(articleTypeDto, articleType);
        if (articleTypeService.updateArticleType(articleType)) {
            return success("修改成功");
        }
        return error("修改失败");
    }

    //查询文章类型所有
    @GetMapping("/type/list")
    public CommonResponse selectArticleTypeAll() {
        return success("查询成功", articleTypeService.selectArticleTypeAll());
    }

    //查询文章类型详情
    @GetMapping("/type/info")
    public CommonResponse<ArticleTypeVo> selectArticleTypeInfo(@RequestParam String articleTypeCode) {
        return success("查询成功", articleTypeService.selectArticleTypeInfo(articleTypeCode));
    }


}
