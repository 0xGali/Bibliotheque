package com.miage.bibliothequeWebapp.model;

import java.util.Date;
import java.util.Objects;

public class ReservationId {

    private String titre_oeuvre;
    private String nom_usager;
    private Date dateReservation;

    // Constructeurs, getters, setters, equals, hashCode
    public ReservationId() {}

    public ReservationId(String titre_oeuvre, String nom_usager, Date dateReservation) {
        this.titre_oeuvre = titre_oeuvre;
        this.nom_usager = nom_usager;
        this.dateReservation = dateReservation;
    }


    public String getNom_usager() {
        return nom_usager;
    }

    public Date getDateReservation() {
        return dateReservation;
    }

    public String getTitre_oeuvre() {
        return titre_oeuvre;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ReservationId that = (ReservationId) o;
        return Objects.equals(titre_oeuvre, that.titre_oeuvre) &&
                Objects.equals(nom_usager, that.nom_usager) &&
                Objects.equals(dateReservation, that.dateReservation);
    }

    @Override
    public int hashCode() {
        return Objects.hash(titre_oeuvre, nom_usager, dateReservation);
    }
}
