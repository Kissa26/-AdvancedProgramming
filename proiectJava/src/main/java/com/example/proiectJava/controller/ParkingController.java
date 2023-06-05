package com.example.proiectJava.controller;

import com.example.proiectJava.business.Parking;
import com.example.proiectJava.service.ParkingService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Validated
public class ParkingController {

    @Autowired
    private ParkingService parkingService;


    @GetMapping(value = "/parkings")
    public List<Parking> getNearestParkings(@RequestParam double lat,
                                            @RequestParam double lon) {

        return parkingService.getNearestParkings(lon, lat);
    }

    @PostMapping( "/parkings")
    public ResponseEntity<Object> addParking(@Valid @RequestBody Parking parking) {

        System.out.println(parking);
        var parkingResponse = parkingService.addParking(parking);

        return ResponseEntity.ok().body(parkingResponse);
    }
}