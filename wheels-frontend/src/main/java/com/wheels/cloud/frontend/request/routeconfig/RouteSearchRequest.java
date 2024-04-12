package com.wheels.cloud.frontend.request.routeconfig;

import com.sxhta.cloud.common.domain.BaseHashEntity;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.Data;

/**
 * 行程配置表
 */
@Data
  @EqualsAndHashCode(callSuper = true)
@Schema(name = "RouteSearchRequest", description = "行程配置表搜索参数")
public class RouteSearchRequest extends BaseHashEntity  implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

                    @Schema(description = "id")
                        private Long id;
            
    
                    @Schema(description = "出发地HASH")
                        private String departure;
            
                    @Schema(description = "目的地HASH")
                        private String destination;
            
                    @Schema(description = "价格")
                        private BigDecimal price;
            
                    @Schema(description = "里程")
                        private String mileage;
            
                    @Schema(description = "备注")
                        private String mark;
            
    
                    @Schema(description = "创建人HASH")
                        private String createBy;
            
    
                    @Schema(description = "修改人HASH")
                        private String updateBy;
            
    
                    @Schema(description = "删除人HASH")
                        private String deleteBy;
            }
