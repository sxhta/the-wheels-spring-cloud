package com.wheels.cloud.backend.service;

import com.wheels.cloud.backend.request.VehicleDto;

public interface IVehicleService {
    Boolean saveVehicle(VehicleDto vehicleDto);

    Boolean deleteVehicle(String vehicleCode);

    Boolean updateVehicle(VehicleDto vehicleDto);

    Object selectVehicleAll();

    Object selectVehicleInfo(String vehicleCode);
}
