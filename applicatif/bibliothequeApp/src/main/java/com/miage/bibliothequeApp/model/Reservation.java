package com.miage.bibliothequeApp.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

import java.util.Date;

@Data
@Entity
@Table(name = "Reservation")
public class Reservation {

    @Id
    private ReservationId id;

    public ReservationId getId() {
        return id;
    }
}
