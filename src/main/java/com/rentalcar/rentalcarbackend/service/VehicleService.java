package com.rentalcar.rentalcarbackend.service;

import com.rentalcar.rentalcarbackend.model.Vehicle;

import java.util.Date;
import java.util.List;

public interface VehicleService {
    List<Vehicle> findAllVehicle();

    Vehicle saveVehicle(Vehicle vehicle);

    List<Vehicle> getFreeVehicle(Date sDate, Date eDate);
}
