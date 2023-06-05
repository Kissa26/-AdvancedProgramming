package com.example.proiectJava.service;

import com.example.proiectJava.entity.UserEntity;
import com.example.proiectJava.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

import static com.example.proiectJava.security.SecurityConfig.passwordEncoder;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepo userRepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        UserEntity userEntity = userRepo.findFirstByMail(username);

        if (userEntity == null){
            throw new UsernameNotFoundException(username);
        }

        return new User(
                userEntity.getUsername(),
                userEntity.getPassword(),
                new ArrayList<>()
        );
    }
    public UserDetailsService userDetailsService(){

        UserDetails ramesh = User.builder()
                .username("ramesh")
                .password(passwordEncoder().encode("password"))
                .roles("USER")
                .build();

        UserDetails admin = User.builder()
                .username("admin")
                .password(passwordEncoder().encode("admin"))
                .roles("ADMIN")
                .build();

        return new UserDetailsServiceImpl(ramesh, admin);
    }
}
