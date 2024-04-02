package com.wheels.cloud.backend.response;

import com.wheels.cloud.backend.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serial;

@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
public class VehicleDto extends BaseEntity {
    @Serial
    private static final long serialVersionUID = 1L;
}
