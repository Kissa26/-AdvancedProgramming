package com.example.proiectJava.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;

import java.util.Collection;

@Entity(name = "my_user")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class UserEntity  {
    @Id
    private String id;

    @NonNull
    private String mail;

    @NonNull
    private String password;

    private boolean isLoged;

    public UserEntity(@NonNull String mail, @NonNull String password, boolean isLoged) {
        this.mail = mail;
        this.password = password;
        this.isLoged = isLoged;
    }


}
