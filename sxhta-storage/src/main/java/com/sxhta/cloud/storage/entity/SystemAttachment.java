package com.sxhta.cloud.storage.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 附件管理表
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("system_attachment")
public class SystemAttachment implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @TableId(value = "att_id", type = IdType.AUTO)
    private Integer attId;

    private String name;

    private String attDir;


    private String sattDir;

    private String attSize;

    private String attType;


    private Integer imageType;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;
}
