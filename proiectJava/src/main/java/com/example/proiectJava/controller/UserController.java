package com.example.proiectJava.controller;

import com.example.proiectJava.business.User;
import com.example.proiectJava.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@Validated
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<String> registerUsser(@RequestParam String email,
                                                @RequestParam String passwd ,
                                                @RequestParam String confirmPasswd){

        String status = userService.register(email, passwd, confirmPasswd);

        return ResponseEntity.ok().body(status);

    }

    @PostMapping("/signin")
    public ResponseEntity<String> authenticateUser(@RequestParam String email, @RequestParam String passwd){

        String status = userService.signin(email, passwd);

        return ResponseEntity.ok().body(status);
    }


    @PostMapping(value = "/user")
    public ResponseEntity<Object> addUser(@Valid @RequestBody User user) {

        var status = userService.addUser(user);

        return ResponseEntity.ok().body(status);
    }

    @PutMapping(value = "/user/pass/{userId}")
    public ResponseEntity<Object> updateUserPassword(@PathVariable String userId, @RequestParam String passwd) {

        String status = userService.updateUserPassword(userId, passwd);

        return ResponseEntity.ok().body(status);
    }

    @PutMapping(value = "/user/mail/{userId}")
    public ResponseEntity<Object> updateUserEmail(@PathVariable String userId, @Valid @RequestBody String email) {

        String status = userService.updateUserEmail(userId, email);

        return ResponseEntity.ok().body(status);
    }
}


