package com.example.proiectJava.repository;

import com.example.proiectJava.entity.ParkingEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ParkingRepo extends JpaRepository<ParkingEntity,String> {
}
