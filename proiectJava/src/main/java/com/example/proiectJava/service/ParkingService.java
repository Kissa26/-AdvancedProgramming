package com.example.proiectJava.service;

import com.example.proiectJava.business.Parking;
import com.example.proiectJava.entity.ParkingEntity;
import com.example.proiectJava.repository.ParkingRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ParkingService {
    @Autowired
    private ParkingRepo parkingRepo;

    public List<Parking> getNearestParkings(double lon, double lat) {

        return parkingRepo.findAll()
                .stream()
                .map(Parking::fromParkingEntity)
                .sorted(Comparator.comparingDouble(p -> calculateDistance(lon, lat, p.getLog(), p.getLat())))
                .collect(Collectors.toList());
    }

    private double calculateDistance(double x1, double y1, double x2, double y2) {
        double deltaX = x2 - x1;
        double deltaY = y2 - y1;
        return Math.sqrt(deltaX * deltaX + deltaY * deltaY);
    }

    public Parking addParking(Parking parking) {

        ParkingEntity parkingEntity = parkingRepo.save(new ParkingEntity(parking.getName(),
                parking.getLat(),
                parking.getLog(),
                parking.getMaxParkingLot(),
                parking.getCurrentParkingLot()));

        return Parking.fromParkingEntity(parkingEntity);
    }
}