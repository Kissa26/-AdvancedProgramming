package com.example.proiectJava.repository;

import com.example.proiectJava.entity.ReservationEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReservationRepo extends JpaRepository<ReservationEntity,String> {
}
