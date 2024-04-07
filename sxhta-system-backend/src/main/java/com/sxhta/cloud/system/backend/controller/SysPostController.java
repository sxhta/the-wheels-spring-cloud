package com.sxhta.cloud.system.backend.controller;


import com.sxhta.cloud.common.utils.poi.ExcelUtil;
import com.sxhta.cloud.common.web.controller.BaseController;
import com.sxhta.cloud.common.web.domain.CommonResponse;
import com.sxhta.cloud.common.web.page.PageRequest;
import com.sxhta.cloud.common.web.page.TableDataInfo;
import com.sxhta.cloud.log.annotation.Log;
import com.sxhta.cloud.log.enums.BusinessType;
import com.sxhta.cloud.remote.domain.SysUser;
import com.sxhta.cloud.remote.vo.SystemUserCacheVo;
import com.sxhta.cloud.security.annotation.RequiresPermissions;
import com.sxhta.cloud.security.service.TokenService;
import com.sxhta.cloud.system.backend.domain.SysPost;
import com.sxhta.cloud.system.backend.service.ISysPostService;
import jakarta.inject.Inject;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 岗位信息操作处理
 */
@RestController
@RequestMapping("/post")
public class SysPostController extends BaseController {

    @Inject
    private ISysPostService postService;

    @Inject
    private TokenService<SystemUserCacheVo, SysUser> tokenService;

    /**
     * 获取岗位列表
     */
    @RequiresPermissions("system:post:list")
    @GetMapping("/list")
    public TableDataInfo<SysPost> list(SysPost post, PageRequest pageRequest) {
        startPage(pageRequest);
        final var list = postService.selectPostList(post);
        return CommonResponse.list(list);
    }

    @Log(title = "岗位管理", businessType = BusinessType.EXPORT)
    @RequiresPermissions("system:post:export")
    @PostMapping("/export")
    public void export(HttpServletResponse response, SysPost post) {
        final var list = postService.selectPostList(post);
        final var util = new ExcelUtil<SysPost>(SysPost.class);
        util.exportExcel(response, list, "岗位数据");
    }

    /**
     * 根据岗位编号获取详细信息
     */
    @RequiresPermissions("system:post:query")
    @GetMapping(value = "/{postId}")
    public CommonResponse<SysPost> getInfo(@PathVariable Long postId) {
        return CommonResponse.success(postService.selectPostById(postId));
    }

    /**
     * 新增岗位
     */
    @RequiresPermissions("system:post:add")
    @Log(title = "岗位管理", businessType = BusinessType.INSERT)
    @PostMapping
    public CommonResponse<Boolean> add(@Validated @RequestBody SysPost post) {
        if (!postService.checkPostNameUnique(post)) {
            return CommonResponse.error("新增岗位'" + post.getPostName() + "'失败，岗位名称已存在");
        } else if (!postService.checkPostCodeUnique(post)) {
            return CommonResponse.error("新增岗位'" + post.getPostName() + "'失败，岗位编码已存在");
        }
        post.setCreateBy(tokenService.getUsername());
        return CommonResponse.result(postService.insertPost(post));
    }

    /**
     * 修改岗位
     */
    @RequiresPermissions("system:post:edit")
    @Log(title = "岗位管理", businessType = BusinessType.UPDATE)
    @PutMapping
    public CommonResponse<Boolean> edit(@Validated @RequestBody SysPost post) {
        if (!postService.checkPostNameUnique(post)) {
            return CommonResponse.error("修改岗位'" + post.getPostName() + "'失败，岗位名称已存在");
        } else if (!postService.checkPostCodeUnique(post)) {
            return CommonResponse.error("修改岗位'" + post.getPostName() + "'失败，岗位编码已存在");
        }
        post.setUpdateBy(tokenService.getUsername());
        return CommonResponse.result(postService.updatePost(post));
    }

    /**
     * 删除岗位
     */
    @RequiresPermissions("system:post:remove")
    @Log(title = "岗位管理", businessType = BusinessType.DELETE)
    @DeleteMapping("/{postIds}")
    public CommonResponse<Boolean> remove(@PathVariable Long[] postIds) {
        return CommonResponse.result(postService.deletePostByIds(postIds));
    }

    /**
     * 获取岗位选择框列表
     */
    @GetMapping("/optionselect")
    public CommonResponse<List<SysPost>> optionselect() {
        final var posts = postService.selectPostAll();
        return CommonResponse.success(posts);
    }
}
