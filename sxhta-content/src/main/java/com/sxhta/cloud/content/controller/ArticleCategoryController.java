package com.sxhta.cloud.content.controller;

import com.sxhta.cloud.common.web.controller.BaseController;
import com.sxhta.cloud.common.web.controller.ICommonController;
import com.sxhta.cloud.common.web.domain.CommonResponse;
import com.sxhta.cloud.common.web.page.PageRequest;
import com.sxhta.cloud.common.web.page.TableDataInfo;
import com.sxhta.cloud.content.request.ArticleCategoryRequest;
import com.sxhta.cloud.content.request.ArticleCategorySearchRequest;
import com.sxhta.cloud.content.response.ArticleCategoryResponse;
import com.sxhta.cloud.content.service.ArticleCategoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.inject.Inject;
import org.springframework.web.bind.annotation.*;

import java.io.Serial;
import java.io.Serializable;

/**
 * 平台文章
 */
@RestController
@RequestMapping("/article/category")
@Tag(name = "文章管理", description = "文章管理控制器")
public class ArticleCategoryController extends BaseController
        implements ICommonController<ArticleCategorySearchRequest, ArticleCategoryRequest, ArticleCategoryResponse>,
        Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Inject
    private ArticleCategoryService articleCategoryService;


    @GetMapping("/list")
    @Operation(summary = "查询文章分类列表")
    @Override
    public TableDataInfo<ArticleCategoryResponse> getAdminList(ArticleCategorySearchRequest request, PageRequest pageRequest) {
        startPage(pageRequest);
        final var list = articleCategoryService.getAdminList(request);
        return CommonResponse.list(list);
    }

    @GetMapping("/all")
    @Operation(summary = "查询文章分类列表")
    public TableDataInfo<ArticleCategoryResponse> getArticleCategoryList() {
        final var list = articleCategoryService.getArticleCategoryList();
        return CommonResponse.list(list);
    }

    @Override
    public CommonResponse<ArticleCategoryResponse> getInfoByHash(@RequestParam String hash) {
        final var result = articleCategoryService.getInfoByHash(hash);
        return CommonResponse.success(result);
    }

    @PostMapping("/create")
    @Operation(summary = "新增分类")
    @Override
    public CommonResponse<Boolean> create(@RequestBody ArticleCategoryRequest request) {
        final var result = articleCategoryService.create(request);
        return CommonResponse.result(result);
    }

    @DeleteMapping("/delete/hard")
    @Operation(summary = "删除分类")
    @Override
    public CommonResponse<Boolean> deleteByHash(@RequestParam(value = "hash") String hash) {
        final var result = articleCategoryService.deleteByHash(hash);
        return CommonResponse.result(result);
    }

    @Override
    @PutMapping("/update")
    public CommonResponse<Boolean> updateEntity(@RequestBody ArticleCategoryRequest request) {
        final var result = articleCategoryService.updateEntity(request);
        return CommonResponse.result(result);
    }

    @DeleteMapping("/delete/soft")
    @Operation(summary = "删除分类")
    public CommonResponse<Boolean> softDeleteByHash(@RequestParam(value = "hash") String hash) {
        final var result = articleCategoryService.softDeleteByHash(hash);
        return CommonResponse.result(result);
    }
}