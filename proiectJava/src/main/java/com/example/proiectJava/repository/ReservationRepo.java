package com.example.proiectJava.repository;

import com.example.proiectJava.entity.ParkingEntity;
import com.example.proiectJava.entity.ReservationEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReservationRepo extends JpaRepository<ReservationEntity, String> {
    ReservationEntity findByIdUserAndIdParkingAndIsActive(String idUser, String idParking, Boolean isActive);
}
