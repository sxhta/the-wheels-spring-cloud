package com.sxhta.cloud.wheels.frontend.controller.authentication;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

//认证
@RestController
@RequestMapping("/certification")
public class CertificationController {

    //车辆认证
    @PostMapping("/car/save")
    public void carCertificationCreate() {

    }

    @GetMapping("/car/info")
    public void carCertificationGetInfo() {

    }

    //TODO:后管查询所有车辆认证列表
    @GetMapping("/car/list")
    public void carCertificationGetList() {

    }


    //司机认证
    @PostMapping("/driver/save")
    public void driverCertificationCreate() {

    }

    @GetMapping("/driver/info")
    public void driverCertificationGetInfo() {

    }

    //TODO:后管查询所有司机认证列表
    @GetMapping("/driver/list")
    public void driverCertificationGetList() {

    }

}
