package com.example.proiectJava.service;

import com.example.proiectJava.entity.ParkingEntity;
import com.example.proiectJava.entity.ReservationEntity;
import com.example.proiectJava.entity.UserEntity;
import com.example.proiectJava.repository.ParkingRepo;
import com.example.proiectJava.repository.ReservationRepo;
import com.example.proiectJava.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
public class ReservationService {

    @Autowired
    private ReservationRepo reservationRepo;

    @Autowired
    private ParkingRepo parkingRepo;
    @Autowired
    private UserRepo userRepo;

    public String reserveParkingLot(String idUser, String idParking) {

        Optional<ParkingEntity> parking = parkingRepo.findById(idParking);
        if (parking.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

        Optional<UserEntity> userInfo = userRepo.findById(idUser);
        if (userInfo.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

        boolean log = userInfo.get().isLoged();

        int current = parking.get().getCurrentParkingLot();
        int max = parking.get().getMaxParkingLot();

        if (log && current + 1 <= max) {

            ParkingEntity parkingEntity = parking.get();
            parkingEntity.setCurrentParkingLot(current + 1);

            ReservationEntity reservationEntity = reservationRepo.save(new ReservationEntity(idUser, idParking, true));
            parkingRepo.save(parkingEntity);

            return reservationEntity.getReservationId();
        } else {
            return "Parking is full";
        }

    }


    public String finishReservation(String idReservation) {

        Optional<ReservationEntity> reservationOptional = reservationRepo.findById(idReservation);
        if (reservationOptional.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

        ReservationEntity reservationEntity = reservationOptional.get();

        Optional<ParkingEntity> parkingOptional = parkingRepo.findById(reservationEntity.getIdParking());
        if (parkingOptional.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

        ParkingEntity parkingEntity = parkingOptional.get();

        reservationEntity.setActive(false);
        parkingEntity.setCurrentParkingLot(parkingEntity.getCurrentParkingLot() - 1);

        reservationRepo.save(reservationEntity);
        parkingRepo.save(parkingEntity);

        return "Thank you for using our services!";
    }

    public String getActiveReservation(String idUser, String idParking) {
        ReservationEntity reservationEntities = reservationRepo.findByIdUserAndIdParkingAndIsActive(idUser, idParking, true);
        return reservationEntities.getReservationId();
    }
}
