package com.example.proiectJava.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import lombok.*;

import java.util.UUID;

@Entity(name = "Reservation")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ReservationEntity {
    @Id
    private String reservationId;
    @NonNull
    private String idUser;

    @NonNull
    private String idParking;

    private boolean isActive;

    public ReservationEntity(String idUser, String idParking, boolean isActive) {
        this.idUser = idUser;
        this.idParking = idParking;
        this.isActive = isActive;
    }

    @PrePersist
    public void generateRandomId() {

        UUID uuid = UUID.randomUUID();

        setReservationId(uuid.toString());
    }
}
