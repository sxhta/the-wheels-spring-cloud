package com.sxhta.cloud.content.controller;


import com.sxhta.cloud.common.constant.FilePathConstants;
import com.sxhta.cloud.common.web.controller.BaseController;
import com.sxhta.cloud.common.web.controller.ICommonController;
import com.sxhta.cloud.common.web.domain.CommonResponse;
import com.sxhta.cloud.common.web.page.PageRequest;
import com.sxhta.cloud.common.web.page.TableDataInfo;
import com.sxhta.cloud.content.request.ArticleRequest;
import com.sxhta.cloud.content.request.ArticleSearchRequest;
import com.sxhta.cloud.content.response.ArticleResponse;
import com.sxhta.cloud.content.response.UploadResponse;
import com.sxhta.cloud.content.service.ArticleService;
import com.sxhta.cloud.content.service.ContentUploadService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.Serial;
import java.io.Serializable;

/**
 * 平台文章
 */
@Slf4j
@RestController
@RequestMapping("/article")
@Tag(name = "文章管理", description = "文章管理控制器")
public class ArticleController extends BaseController
        implements ICommonController<ArticleSearchRequest, ArticleRequest, ArticleResponse>,
        Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Inject
    private ArticleService articleService;

    @Inject
    private ContentUploadService contentUploadService;


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
    public CommonResponse<Boolean> updateEntity(@RequestBody ArticleRequest request) {
        final var result = articleService.updateEntity(request);
        return CommonResponse.result(result);
    }

    @PostMapping("/upload/thumb")
    public CommonResponse<UploadResponse> uploadThumb(@RequestParam("file") MultipartFile file) {
        final var response = contentUploadService.uploadFile(file, FilePathConstants.ARTICLE_THUMB_PATH);
        return CommonResponse.success("上传成功", response);
    }

    @PostMapping("/upload/image")
    public CommonResponse<UploadResponse> uploadImage(@RequestParam("file") MultipartFile file) {
        final var response = contentUploadService.uploadFile(file, FilePathConstants.ARTICLE_IMAGE_PATH);
        return CommonResponse.success("上传成功", response);
    }

    @PostMapping("/upload/content")
    public CommonResponse<UploadResponse> uploadContent(@RequestParam("file") MultipartFile file) {
        final var response = contentUploadService.uploadFile(file, FilePathConstants.ARTICLE_CONTENT_PATH);
        return CommonResponse.success("上传成功", response);
    }
}
