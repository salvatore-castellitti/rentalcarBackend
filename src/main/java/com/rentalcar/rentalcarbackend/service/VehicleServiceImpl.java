package com.rentalcar.rentalcarbackend.service;

import com.rentalcar.rentalcarbackend.model.Vehicle;
import com.rentalcar.rentalcarbackend.repository.VehicleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;

@Service
@Transactional
public class VehicleServiceImpl implements VehicleService{

    @Autowired
    private VehicleRepository vehicleRepository;

    @PersistenceContext
    protected EntityManager entityManager;


    @Override
    public List<Vehicle> findAllVehicle(){
        return vehicleRepository.findAll();
    }

    @Override
    public Vehicle saveVehicle(Vehicle vehicle){
        return vehicleRepository.save(vehicle);
    }

    @Override
    public List<Vehicle> getFreeVehicle(Date sDate, Date eDate) {
        return vehicleRepository.getFreeVehicle(sDate,eDate);
    }
}
