package com.sxhta.cloud.system.backend.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.extension.toolkit.SqlHelper;
import com.sxhta.cloud.common.constant.UserConstants;
import com.sxhta.cloud.common.exception.ServiceException;
import com.sxhta.cloud.system.backend.domain.SysPost;
import com.sxhta.cloud.system.backend.mapper.SysPostMapper;
import com.sxhta.cloud.system.backend.mapper.SysUserPostMapper;
import com.sxhta.cloud.system.backend.service.ISysPostService;
import jakarta.inject.Inject;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 岗位信息 服务层处理
 */
@Service
public class SysPostServiceImpl implements ISysPostService {

    @Inject
    private SysPostMapper postMapper;

    @Inject
    private SysUserPostMapper userPostMapper;

    /**
     * 查询岗位信息集合
     *
     * @param post 岗位信息
     * @return 岗位信息集合
     */
    @Override
    public List<SysPost> selectPostList(SysPost post) {
        return postMapper.selectPostList(post);
    }

    /**
     * 查询所有岗位
     *
     * @return 岗位列表
     */
    @Override
    public List<SysPost> selectPostAll() {
        return postMapper.selectPostAll();
    }

    /**
     * 通过岗位ID查询岗位信息
     *
     * @param postId 岗位ID
     * @return 角色对象信息
     */
    @Override
    public SysPost selectPostById(Long postId) {
        return postMapper.selectPostById(postId);
    }

    /**
     * 根据用户ID获取岗位选择框列表
     *
     * @param userId 用户ID
     * @return 选中岗位ID列表
     */
    @Override
    public List<Long> selectPostListByUserId(Long userId) {
        return postMapper.selectPostListByUserId(userId);
    }

    /**
     * 校验岗位名称是否唯一
     *
     * @param post 岗位信息
     * @return 结果
     */
    @Override
    public Boolean checkPostNameUnique(SysPost post) {
        final var postId = ObjectUtil.isNull(post.getPostId()) ? -1L : post.getPostId();
        final var info = postMapper.checkPostNameUnique(post.getPostName());
        if (ObjectUtil.isNotNull(info) && !info.getPostId().equals(postId)) {
            return UserConstants.NOT_UNIQUE;
        }
        return UserConstants.UNIQUE;
    }

    /**
     * 校验岗位编码是否唯一
     *
     * @param post 岗位信息
     * @return 结果
     */
    @Override
    public Boolean checkPostCodeUnique(SysPost post) {
        final var postId = ObjectUtil.isNull(post.getPostId()) ? -1L : post.getPostId();
        final var info = postMapper.checkPostCodeUnique(post.getPostCode());
        if (ObjectUtil.isNotNull(info) && !info.getPostId().equals(postId)) {
            return UserConstants.NOT_UNIQUE;
        }
        return UserConstants.UNIQUE;
    }

    /**
     * 通过岗位ID查询岗位使用数量
     *
     * @param postId 岗位ID
     * @return 结果
     */
    @Override
    public Integer countUserPostById(Long postId) {
        return userPostMapper.countUserPostById(postId);
    }

    /**
     * 删除岗位信息
     *
     * @param postId 岗位ID
     * @return 结果
     */
    @Override
    public Integer deletePostById(Long postId) {
        return postMapper.deletePostById(postId);
    }

    /**
     * 批量删除岗位信息
     *
     * @param postIds 需要删除的岗位ID
     * @return 结果
     */
    @Override
    public Boolean deletePostByIds(Long[] postIds) {
        for (final var postId : postIds) {
            final var post = selectPostById(postId);
            if (countUserPostById(postId) > 0) {
                throw new ServiceException(String.format("%1$s已分配,不能删除", post.getPostName()));
            }
        }
        return SqlHelper.retBool(postMapper.deletePostByIds(postIds));
    }

    /**
     * 新增保存岗位信息
     *
     * @param post 岗位信息
     * @return 结果
     */
    @Override
    public Boolean insertPost(SysPost post) {
        return SqlHelper.retBool(postMapper.insertPost(post));
    }

    /**
     * 修改保存岗位信息
     *
     * @param post 岗位信息
     * @return 结果
     */
    @Override
    public Boolean updatePost(SysPost post) {
        return SqlHelper.retBool(postMapper.updatePost(post));
    }
}