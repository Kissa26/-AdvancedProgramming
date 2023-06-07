package com.example.proiectJava.business;

import com.example.proiectJava.entity.ParkingEntity;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Parking {

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

    public static Parking fromParkingEntity(@NotNull ParkingEntity entity) {
        return Parking.builder().name(entity.getName()).log(entity.getLog()).lat(entity.getLat()).maxParkingLot(entity.getMaxParkingLot()).currentParkingLot(entity.getCurrentParkingLot()).build();
    }

}
