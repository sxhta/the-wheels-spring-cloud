package com.sxhta.cloud.content.controller;


import com.sxhta.cloud.common.web.domain.CommonResponse;
import com.sxhta.cloud.content.service.ArticleService;
import com.sxhta.cloud.content.service.IArticleTypeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.inject.Inject;
import org.springframework.web.bind.annotation.*;

import static com.sxhta.cloud.common.web.domain.CommonResponse.error;
import static com.sxhta.cloud.common.web.domain.CommonResponse.success;

/**
 * 平台文章
 */
@RestController
@RequestMapping("/article")
@Tag(name = "文章管理", description = "文章管理控制器")
public class ArticleController {
    @Inject
    private ArticleService articleService;

    @Inject
    private IArticleTypeService articleTypeService;


    /**
     * 平台文章新增
     *
     * @param articleDto 平台文章web数据
     * @return true新增成功 false新增失败
     */
    @Operation(summary = "新增文章")
    @PostMapping("/saveArticle")
    public CommonResponse<String> saveArticle(@RequestBody ArticleDto articleDto) {
        //TODO:平台文章web数据，判断必填项数据是否填写，其他数据是否填写正确
        if (articleService.saveArticle(articleDto)) {
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
    @DeleteMapping("/deleteArticle")
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
    @PutMapping("/updateArticle")
    public CommonResponse<String> updateArticle(@RequestBody ArticleDto articleDto) {
        //TODO:平台文章web数据，判断必填项数据是否填写，其他数据是否填写正确
        if (articleService.updateArticle(articleDto)) {
            return success("修改成功");
        }
        return error("修改失败");
    }

    //查询平台文章所有
    @GetMapping("/articleList")
    public CommonResponse selectArticleAll() {
        return success("查询成功", articleService.selectArticleAll());
    }

    //查询平台文章详情
    @GetMapping("/articleInfo")
    public CommonResponse selectArticleInfo() {
        return success("查询成功", articleService.selectArticleInfo());
    }

    /**
     * 文章类型新增
     *
     * @param articleDto 文章类型web数据
     * @return true新增成功 false新增失败
     */
    @PostMapping("/saveType")
    public CommonResponse<String> saveArticleType(@RequestBody ArticleDto articleDto) {
        //TODO:平台文章web数据，判断必填项数据是否填写，其他数据是否填写正确
        if (articleTypeService.saveArticleType(articleDto)) {
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
    @DeleteMapping("/deleteType")
    public CommonResponse<String> deleteArticleType(@RequestParam String articleTypeCode) {
        //TODO:判断articleCode是否为null
        if (articleTypeService.deleteArticleType(articleTypeCode)) {
            return success("删除成功");
        }
        return error("删除失败");
    }

    /**
     * 文章类型修改
     *
     * @param articleDto 文章类型web数据
     * @return true修改成功 false修改失败
     */
    @PutMapping("/updateType")
    public CommonResponse<String> updateArticleType(@RequestBody ArticleDto articleDto) {
        //TODO:平台文章web数据，判断必填项数据是否填写，其他数据是否填写正确
        if (articleTypeService.updateArticleType(articleDto)) {
            return success("修改成功");
        }
        return error("修改失败");
    }

    //查询文章类型所有
    @GetMapping("/typeList")
    public CommonResponse selectArticleTypeAll() {
        return success("查询成功", articleTypeService.selectArticleTypeAll());
    }

    //查询文章类型详情
    @GetMapping("/typeInfo")
    public CommonResponse selectArticleTypeInfo() {
        return success("查询成功", articleTypeService.selectArticleTypeInfo());
    }


}
