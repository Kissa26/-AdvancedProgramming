package com.example.proiectJava.controller;

import com.example.proiectJava.service.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@Validated
public class ReservationController {
    @Autowired
    private ReservationService reservationService;

    @PostMapping(value = "/reservations")
    public String reserveParkingLot(@RequestParam String idUser, @RequestParam String idParking) {
        return reservationService.reserveParkingLot(idUser, idParking);
    }

    @PutMapping(value = "/reservations")
    public String finishReservation(@RequestParam String idReservation) {

        return reservationService.finishReservation(idReservation);
    }

    @GetMapping(value ="/reservations")
    public String getActiveReservation(@RequestParam String idUser, @RequestParam String idParking) {
        return reservationService.getActiveReservation(idUser, idParking);
    }
}
