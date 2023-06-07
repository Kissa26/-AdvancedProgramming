package com.example.proiectJava.business;

import com.example.proiectJava.entity.UserEntity;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class User {

    @NotNull(message = "Email  cannot be null")
    private String email;

    @NotNull(message = "Password  cannot be null")
    private String password;

    @NotNull
    private boolean isLoged;

    public static User fromUserEntity(@NotNull UserEntity entity) {
        return User.builder().email(entity.getMail()).password(entity.getPassword()).isLoged(entity.isLoged()).build();
    }
}
