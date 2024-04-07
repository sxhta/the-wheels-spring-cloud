package com.sxhta.cloud.common.domain;

import com.sxhta.cloud.common.utils.encrypt.EncryptUtil;
import com.sxhta.cloud.common.utils.uuid.UUID;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public abstract class BaseHashEntity {

    private String hash = EncryptUtil.generateEntityHash(UUID.randomUUID(true).toString());

    private LocalDateTime createTime;

    private LocalDateTime updateTime;

    private LocalDateTime deleteTime;
}
