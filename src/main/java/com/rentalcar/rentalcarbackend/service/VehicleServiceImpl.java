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
        TypedQuery<Vehicle> query = entityManager.createQuery(
                "SELECT v from Vehicle v where v.id not in (select ve.id from Vehicle as ve join ve.reservation as verRes  where verRes.endDate > (?1) AND verRes.startDate < (?2))" , Vehicle.class);
        List<Vehicle> vehciles = query.setParameter(1, sDate).setParameter(2, eDate).getResultList();

        return vehciles;
    }
}
