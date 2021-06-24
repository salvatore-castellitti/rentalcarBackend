package com.rentalcar.rentalcarbackend.repository;

import com.rentalcar.rentalcarbackend.model.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface VehicleRepository extends JpaRepository<Vehicle, Long> {

    @Query(value = "SELECT v.* FROM vehicles as v WHERE v.id NOT IN (SELECT ve.id FROM vehicles as ve JOIN reservation AS verRes ON ve.id = verRes.vehicle WHERE verRes.end_Date > ?1 and verRes.start_Date < ?2)",nativeQuery = true)
    List<Vehicle> getFreeVehicle(Date sDate, Date eDate);
}
