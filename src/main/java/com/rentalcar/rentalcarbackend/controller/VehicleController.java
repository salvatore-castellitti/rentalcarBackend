package com.rentalcar.rentalcarbackend.controller;

import com.rentalcar.rentalcarbackend.exception.ResourceNotFoundException;
import com.rentalcar.rentalcarbackend.model.Vehicle;
import com.rentalcar.rentalcarbackend.repository.VehicleRepository;
import com.rentalcar.rentalcarbackend.service.VehicleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/")
@CrossOrigin(origins = "http://localhost:4200")
public class VehicleController {

    @Autowired
    private VehicleRepository vehicleRepository;

    @Autowired
    private VehicleService vehicleService;

    @GetMapping("/vehicles")
    public List<Vehicle> getListVehicle(){
       return vehicleRepository.findAll();
    }

    @PostMapping("/vehicles")
    public Vehicle createVehicle(@RequestBody Vehicle vehicle){
        return  vehicleRepository.save(vehicle);
    }

    //get vehicle by id
    @GetMapping("/vehicles/{id}")
    public ResponseEntity<Vehicle> getVehicleById(@PathVariable Long id){
        Vehicle vehicle = vehicleRepository.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("Vehicle not exist with id :" + id ));
        return ResponseEntity.ok(vehicle);
    }

    //delete vehicle
    @DeleteMapping("/vehicles/{id}")
    public ResponseEntity<Map<String, Boolean>> deleteVehicle(@PathVariable Long id){
        Vehicle vehicle = vehicleRepository.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("Vehicle not exist with id :" + id ));

        vehicleRepository.delete(vehicle);
        Map<String,Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/vehicles/{sDate}/{eDate}")
    public List<Vehicle> getFreeVehicle(@PathVariable Date sDate, @PathVariable Date eDate) {
        return vehicleService.getFreeVehicle(sDate, eDate);
    }
}
