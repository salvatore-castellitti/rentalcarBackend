package com.rentalcar.rentalcarbackend.repository;

import com.rentalcar.rentalcarbackend.model.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VehicleRepository extends JpaRepository<Vehicle, Long> {


}
