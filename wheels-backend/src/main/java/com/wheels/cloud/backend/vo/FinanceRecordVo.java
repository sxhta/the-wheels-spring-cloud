package com.wheels.cloud.backend.vo;

import com.wheels.cloud.backend.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serial;

/**
 * 财务记录视图对象
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
public class FinanceRecordVo extends BaseEntity {
    @Serial
    private static final long serialVersionUID = 1L;
}
