package com.example.proiectJava.repository;

import com.example.proiectJava.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepo extends JpaRepository<UserEntity, String> {

    UserEntity findFirstByMail(String mail);
}
