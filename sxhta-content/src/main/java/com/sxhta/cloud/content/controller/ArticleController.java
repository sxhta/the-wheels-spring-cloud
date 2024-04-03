package com.sxhta.cloud.content.controller;


import com.sxhta.cloud.common.web.controller.BaseController;
import com.sxhta.cloud.common.web.controller.ICommonController;
import com.sxhta.cloud.common.web.domain.CommonResponse;
import com.sxhta.cloud.common.web.page.PageRequest;
import com.sxhta.cloud.common.web.page.TableDataInfo;
import com.sxhta.cloud.content.request.ArticleRequest;
import com.sxhta.cloud.content.request.ArticleSearchRequest;
import com.sxhta.cloud.content.response.ArticleResponse;
import com.sxhta.cloud.content.service.ArticleService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.inject.Inject;
import org.springframework.web.bind.annotation.*;

/**
 * 平台文章
 */
@RestController
@RequestMapping("/article")
@Tag(name = "文章管理", description = "文章管理控制器")
public class ArticleController extends BaseController implements ICommonController<ArticleSearchRequest, ArticleRequest, ArticleResponse> {

    @Inject
    private ArticleService articleService;


    @Override
    @GetMapping("/list")
    public TableDataInfo<ArticleResponse> getAdminList(ArticleSearchRequest request, PageRequest pageRequest) {
        startPage(pageRequest);
        final var list = articleService.getAdminList(request);
        return CommonResponse.list(list);
    }

    @Override
    @GetMapping("/info")
    public CommonResponse<ArticleResponse> getInfoByHash(@RequestParam("hash") String hash) {
        final var result = articleService.getInfoByHash(hash);
        return CommonResponse.success(result);
    }

    @Override
    @PostMapping("/save")
    public CommonResponse<Boolean> create(@RequestBody ArticleRequest request) {
        final var result = articleService.create(request);
        return CommonResponse.result(result);
    }

    @Override
    public CommonResponse<Boolean> softDeleteByHash(@RequestParam String hash) {
        final var result = articleService.softDeleteByHash(hash);
        return CommonResponse.result(result);
    }

    @Override
    @DeleteMapping("/delete")
    public CommonResponse<Boolean> deleteByHash(@RequestParam String hash) {
        final var result = articleService.deleteByHash(hash);
        return CommonResponse.result(result);
    }

    @Override
    @PutMapping("/update")
    public CommonResponse<Boolean> updateCategory(@RequestBody ArticleRequest request) {
        final var result = articleService.updateCategory(request);
        return CommonResponse.result(result);
    }
}
