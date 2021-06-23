package com.rentalcar.rentalcarbackend.service;

import com.rentalcar.rentalcarbackend.model.Reservation;

import java.util.List;

public interface ReservationService {
    List<Reservation> findAllReservations();

    Reservation saveReservation(Reservation reservation);
}
