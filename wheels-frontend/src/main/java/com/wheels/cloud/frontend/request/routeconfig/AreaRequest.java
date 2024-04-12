package com.wheels.cloud.frontend.request.routeconfig;


import com.sxhta.cloud.common.domain.BaseHashEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.EqualsAndHashCode;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.Data;

/**
 * 地区配置表
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Schema(name = "AreaRequest", description = "地区配置表请求体")
public class AreaRequest extends BaseHashEntity implements Serializable{

@Serial
private static final long serialVersionUID=1L;

                    @Schema(description = "ID")
                        private Long id;

                                    @Schema(description = "地区名称")
                        private String areaName;

                                    @Schema(description = "创建人HASH")
                        private String createBy;

                                    @Schema(description = "修改人HASH")
                        private String updateBy;

                                    @Schema(description = "删除人")
                        private String deleteBy;

            }