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

    public String getNom_Usager(){
        return id.getNom_usager();
    }

    public String getTitre_oeuvre(){
        return id.getTitre_oeuvre();
    }

    public Date getDateReservation(){
        return id.getDateReservation();
    }
}
