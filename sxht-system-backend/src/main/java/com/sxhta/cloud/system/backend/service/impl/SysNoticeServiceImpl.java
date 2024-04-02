package com.sxhta.cloud.system.backend.service.impl;

import com.baomidou.mybatisplus.extension.toolkit.SqlHelper;
import com.sxhta.cloud.system.backend.domain.SysNotice;
import com.sxhta.cloud.system.backend.mapper.SysNoticeMapper;
import com.sxhta.cloud.system.backend.service.ISysNoticeService;
import jakarta.inject.Inject;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 公告 服务层实现
 */
@Service
public class SysNoticeServiceImpl implements ISysNoticeService {
    @Inject
    private SysNoticeMapper noticeMapper;

    /**
     * 查询公告信息
     *
     * @param noticeId 公告ID
     * @return 公告信息
     */
    @Override
    public SysNotice selectNoticeById(Long noticeId) {
        return noticeMapper.selectNoticeById(noticeId);
    }

    /**
     * 查询公告列表
     *
     * @param notice 公告信息
     * @return 公告集合
     */
    @Override
    public List<SysNotice> selectNoticeList(SysNotice notice) {
        return noticeMapper.selectNoticeList(notice);
    }

    /**
     * 新增公告
     *
     * @param notice 公告信息
     * @return 结果
     */
    @Override
    public Boolean insertNotice(SysNotice notice) {
        return SqlHelper.retBool(noticeMapper.insertNotice(notice));
    }

    /**
     * 修改公告
     *
     * @param notice 公告信息
     * @return 结果
     */
    @Override
    public Boolean updateNotice(SysNotice notice) {
        return SqlHelper.retBool(noticeMapper.updateNotice(notice));
    }

    /**
     * 删除公告对象
     *
     * @param noticeId 公告ID
     * @return 结果
     */
    @Override
    public Integer deleteNoticeById(Long noticeId) {
        return noticeMapper.deleteNoticeById(noticeId);
    }

    /**
     * 批量删除公告信息
     *
     * @param noticeIds 需要删除的公告ID
     * @return 结果
     */
    @Override
    public Boolean deleteNoticeByIds(Long[] noticeIds) {
        return SqlHelper.retBool(noticeMapper.deleteNoticeByIds(noticeIds));
    }
}
