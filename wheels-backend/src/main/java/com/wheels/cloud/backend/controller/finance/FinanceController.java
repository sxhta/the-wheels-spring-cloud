package com.wheels.cloud.backend.controller.finance;

import com.sxhta.cloud.common.web.domain.CommonResponse;
import com.wheels.cloud.backend.dto.FinanceRecordDto;
import com.wheels.cloud.backend.service.IFinanceRecordService;
import jakarta.inject.Inject;
import org.springframework.web.bind.annotation.*;

import static com.sxhta.cloud.common.web.domain.CommonResponse.error;
import static com.sxhta.cloud.common.web.domain.CommonResponse.success;

/**
 * 财务管理
 */
@RestController
@RequestMapping("/finance")
public class FinanceController {
    @Inject
    private IFinanceRecordService financeRecordService;

    /**
     * 财务记录新增
     *
     * @param financeRecordDto 财务记录web数据
     * @return true新增成功 false新增失败
     */
    @PostMapping("/saveFinanceRecord")
    public CommonResponse<String> saveFinanceRecord(FinanceRecordDto financeRecordDto) {
        //TODO:财务记录web数据，判断必填项数据是否填写，其他数据是否填写正确
        if (financeRecordService.saveFinanceRecord(financeRecordDto)) {
            return success("新增成功");
        }
        return error("新增失败");
    }

    /**
     * 财务记录删除
     *
     * @param financeRecordCode 财务记录编号
     * @return true删除成功 false删除失败
     */
    @DeleteMapping("/deleteFinanceRecord")
    public CommonResponse<String> deleteFinanceRecord(String financeRecordCode) {
        //TODO:财务编号是否未null
        if (financeRecordService.deleteFinanceRecord(financeRecordCode)) {
            return success("删除成功");
        }
        return error("删除失败");
    }

    /**
     * 财务记录修改
     *
     * @param financeRecordDto 财务记录web修改数据
     * @return true修改成功 false修改失败
     */
    @PutMapping("/updateFinanceRecord")
    public CommonResponse<String> updateFinanceRecord(FinanceRecordDto financeRecordDto) {
        //TODO:财务记录web数据，判断必填项数据是否填写，其他数据是否填写正确
        if (financeRecordService.updateFinanceRecord(financeRecordDto)) {
            return success("修改成功");
        }
        return error("修改失败");
    }

    //查询全部财务记录
    @GetMapping("/selectFinanceRecordList")
    public CommonResponse selectFinanceRecordAll() {
        //TODO:判断模糊查询web数据
        return success("查询成功", financeRecordService.selectFinanceRecordAll());

    }

    //查询财务记录详情
    @GetMapping("/selectFinanceRecordList")
    public CommonResponse selectFinanceRecordInfo(String financeRecordCode) {
        return success("查询成功", financeRecordService.selectFinanceRecordInfo(financeRecordCode));

    }


}
