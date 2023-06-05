package com.example.proiectJava.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.UUID;

@Entity(name = "Parking")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ParkingEntity {

    @Id
    private String id;

    @NotNull(message = "Parking Name cannot be null")
    private String name;

    @NotNull(message = "Longitude position cannot be null")
    private double log;

    @NotNull(message = "Latitude position cannot be null")
    private double lat;
    @NotNull
    private int maxParkingLot;

    @NotNull
    private int currentParkingLot;

    public ParkingEntity(String name, double log, double lat, int maxParkingLot, int currentParkingLot) {
        this.name = name;
        this.log = log;
        this.lat = lat;
        this.maxParkingLot = maxParkingLot;
        this.currentParkingLot = currentParkingLot;
    }

    @PrePersist
    public void generateRandomId() {

        UUID uuid = UUID.randomUUID();

        setId(uuid.toString());
    }
}
