package com.miage.bibliothequeWebapp.model;

import java.util.Date;

public class Reservation {

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
