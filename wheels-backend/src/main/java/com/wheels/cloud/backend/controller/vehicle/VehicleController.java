package com.wheels.cloud.backend.controller.vehicle;

import com.sxhta.cloud.common.web.domain.CommonResponse;
import com.wheels.cloud.backend.request.VehicleDto;
import com.wheels.cloud.backend.service.IVehicleService;
import jakarta.inject.Inject;
import org.springframework.web.bind.annotation.*;

import static com.sxhta.cloud.common.web.domain.CommonResponse.error;
import static com.sxhta.cloud.common.web.domain.CommonResponse.success;

@RestController
@RequestMapping("/vehicle")
public class VehicleController {
    @Inject
    private IVehicleService vehicleService;

    /**
     * 新增车辆
     *
     * @param vehicleDto 车辆web新增数据
     * @return true新增成功 false新增失败
     */
    @PostMapping("/save")
    public CommonResponse<String> saveVehicle(VehicleDto vehicleDto) {
        //TODO:车辆web数据，判断必填项数据是否填写，其他数据是否填写正确
        if (vehicleService.saveVehicle(vehicleDto)) {
            return success("新增成功");
        }
        return error("新增失败");
    }

    /**
     * 删除车辆
     *
     * @param vehicleCode 车辆编号
     * @return true删除成功 false删除失败
     */
    @DeleteMapping("/deleteVehicle")
    public CommonResponse<String> deleteVehicle(String vehicleCode) {
        //TODO:vehicleCode车辆编号是否为null
        if (vehicleService.deleteVehicle(vehicleCode)) {
            return success("删除成功");
        }
        return error("删除失败");
    }

    /**
     * 修改车辆
     *
     * @param vehicleDto 车辆web数据
     * @return true修改成功 false修改失败
     */
    @PutMapping("/updateVehicle")
    public CommonResponse<String> updateVehicle(VehicleDto vehicleDto) {
        //TODO:车辆web数据，判断必填项数据是否填写，其他数据是否填写正确
        if (vehicleService.updateVehicle(vehicleDto)) {
            return success("修改成功");
        }
        return error("修改失败");
    }

    //查询所有车辆
    @GetMapping("/selectVehicleList")
    public CommonResponse selectVehicleAll() {
        //TODO:模糊数据判断
        return success("查询成功", vehicleService.selectVehicleAll());
    }

    //查询该车辆详情
    @GetMapping("/selectVehicleInfo")
    public CommonResponse selectVehicleInfo(String vehicleCode) {
        //TODO:判断vehicleCode是否为null
        return success("查询成功", vehicleService.selectVehicleInfo(vehicleCode));
    }


}
