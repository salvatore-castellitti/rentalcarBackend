package com.rentalcar.rentalcarbackend.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "vehicles")
public class Vehicle {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name = "type")
    private String type;
    @Column(name = "house_producer")
    private String houseProducer;
    @Column(name = "model")
    private String model;
    @Column(name = "license_plate")
    private String licensePlate;
    @OneToMany(mappedBy = "vehicle", cascade = CascadeType.ALL)
    @JsonIgnore
    private Set<Reservation> reservation;

    public Vehicle() {
    }

    public Vehicle(String type, String houseProducer, String model, String licensePlate) {
        this.type = type;
        this.houseProducer = houseProducer;
        this.model = model;
        this.licensePlate = licensePlate;
    }

    public Vehicle(String type, String houseProducer, String model, String licensePlate, Set<Reservation> reservation) {
        this.type = type;
        this.houseProducer = houseProducer;
        this.model = model;
        this.licensePlate = licensePlate;
        this.reservation = reservation;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getHouseProducer() {
        return houseProducer;
    }

    public void setHouseProducer(String houseProducer) {
        this.houseProducer = houseProducer;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getLicensePlate() {
        return licensePlate;
    }

    public void setLicensePlate(String licensePlate) {
        this.licensePlate = licensePlate;
    }

    public Set<Reservation> getReservation() {
        return reservation;
    }

    public void setReservation(Set<Reservation> reservation) {
        this.reservation = reservation;
    }
}
