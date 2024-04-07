package com.sxhta.cloud.storage.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sxhta.cloud.storage.entity.SystemAttachment;
import org.apache.ibatis.annotations.Mapper;

/**
 * 附件管理表 Mapper 接口

 */
@Mapper
public interface AttachmentMapper extends BaseMapper<SystemAttachment> {

}
