package com.wheels.cloud.backend.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serial;

/**
 * 财务记录实体类
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
public class FinanceRecord extends BaseEntity {
    @Serial
    private static final long serialVersionUID = 1L;
}
