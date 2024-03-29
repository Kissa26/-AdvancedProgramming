package com.example.proiectJava.service;

import com.example.proiectJava.business.User;
import com.example.proiectJava.entity.UserEntity;
import com.example.proiectJava.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserRepo userRepo;

    public String register(String email, String passwd, String confirmPasswd) {
        UserEntity userOpt = userRepo.findFirstByMail(email);

        if (userOpt != null) {
            return "The email already exists!";
        }
        if (!confirmPasswd.equals(passwd)) {
            return "Passwords do not match";
        }
        User userNew = new User(email, passwd, true);
        addUser(userNew);
        return "User registered successfully!";
    }

    public String signIn(String email, String passwd) {
        UserEntity userOpt = userRepo.findFirstByMail(email);

        if (userOpt == null) {
            return "The email is not in the database!";
        }
        if (!userOpt.getPassword().equals(passwd)) {
            return "Incorrect password";
        }
        Authentication authentication = new UsernamePasswordAuthenticationToken(userOpt.getId(), passwd);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return "User signed-in successfully!";
    }

    public String addUser(User user) {
        userRepo.save(new UserEntity(user.getEmail(), user.getPassword(), user.isLoged()));
        return "Successfully registered!";
    }

    public String updateUserPassword(String userId, String newPasswd) {

        Optional<UserEntity> userOpt = userRepo.findById(userId);

        if (userOpt.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

        UserEntity userEntity = userOpt.get();
        userEntity.setPassword(newPasswd);

        userRepo.save(userEntity);

        return "Successfully updated!";
    }

    public String updateUserEmail(String userId, String newEmail) {

        Optional<UserEntity> userOpt = userRepo.findById(userId);
        if (userOpt.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

        UserEntity userEntity = userOpt.get();
        userEntity.setMail(newEmail);

        userRepo.save(userEntity);

        return "Successfully updated!";
    }
}
