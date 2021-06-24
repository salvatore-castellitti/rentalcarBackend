package com.rentalcar.rentalcarbackend.controller;

import com.rentalcar.rentalcarbackend.exception.ResourceNotFoundException;
import com.rentalcar.rentalcarbackend.model.Reservation;
import com.rentalcar.rentalcarbackend.model.User;
import com.rentalcar.rentalcarbackend.model.Vehicle;
import com.rentalcar.rentalcarbackend.repository.ReservationRepository;
import com.rentalcar.rentalcarbackend.service.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/")
@CrossOrigin(origins = "http://localhost:4200")
public class ReservationController {
    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private ReservationService reservationService;

    @GetMapping("/reservations/list")
    public List<Reservation> getAllReservation(){
        return reservationService.findAllReservations();
    }

    @PostMapping("/reservations/add")
    public Reservation createReservation(@RequestBody Reservation reservation){
        return  reservationService.saveReservation(reservation);
    }

    //get reservation by id
    @GetMapping("/reservations/get/{id}")
    public ResponseEntity<Reservation> getReservationById(@PathVariable Long id){
        Reservation reservation = reservationRepository.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("reservation not exist with id :" + id ));
        return ResponseEntity.ok(reservation);
    }

    //delete reservation
    @DeleteMapping("/reservations/delete/{id}")
    public ResponseEntity<Map<String, Boolean>> deleteReservation(@PathVariable Long id){
        Reservation reservation = reservationRepository.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("reservation not exist with id :" + id ));

        reservationRepository.delete(reservation);
        Map<String,Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return ResponseEntity.ok(response);
    }

}
