package com.rentalcar.rentalcarbackend.service;

import com.rentalcar.rentalcarbackend.model.Reservation;
import com.rentalcar.rentalcarbackend.repository.ReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class ReservationServiceImpl implements ReservationService{

    @Autowired
    private ReservationRepository reservationRepository;

    @Override
    public List<Reservation> findAllReservations(){
        return reservationRepository.findAll();
    }

    @Override
    public Reservation saveReservation(Reservation reservation){
        return reservationRepository.save(reservation);
    }
}
