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
 * 其他费用配置表
 */
@Data
  @EqualsAndHashCode(callSuper = true)
@Schema(name = "OtherFeeSearchRequest", description = "其他费用配置表搜索参数")
public class OtherFeeSearchRequest extends BaseHashEntity  implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

                    @Schema(description = "ID")
                        private Long id;
            
    
                    @Schema(description = "项目名称")
                        private String name;
            
                    @Schema(description = "价格")
                        private BigDecimal price;
            
                    @Schema(description = "备注")
                        private String remark;
            
    
                    @Schema(description = "创建人HASH")
                        private String createBy;
            
    
                    @Schema(description = "修改人HASH")
                        private String updateBy;
            
    
                    @Schema(description = "删除人HASh")
                        private String deleteBy;
            }
