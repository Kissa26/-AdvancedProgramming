package com.example.proiectJava.controller;

import com.example.proiectJava.business.User;
import com.example.proiectJava.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@Validated
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private AuthenticationManager authenticationManager;
    @PostMapping("/signin")
    public ResponseEntity<String> authenticateUser(@RequestBody User user){
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                user.getEmail(), user.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        return new ResponseEntity<>("User signed-in successfully!.", HttpStatus.OK);
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


