package com.example.proiectJava.repository;

import com.example.proiectJava.entity.ParkingEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ParkingRepo extends JpaRepository<ParkingEntity, String> {
    List<ParkingEntity> findByName(String name);
}
