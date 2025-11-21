package com.miage.bibliothequeApp.model;

import jakarta.persistence.Embeddable;
import lombok.Data;

import java.util.Date;
import java.util.Objects;

@Embeddable
@Data
public class ReservationId {

    private String titre_oeuvre;
    private String nom_usager;
    private Date date_reservation;

    // Constructeurs, getters, setters, equals, hashCode
    public ReservationId() {}

    public ReservationId(String titre_oeuvre, String nom_usager, Date date_reservation) {
        this.titre_oeuvre = titre_oeuvre;
        this.nom_usager = nom_usager;
        this.date_reservation = date_reservation;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ReservationId that = (ReservationId) o;
        return Objects.equals(titre_oeuvre, that.titre_oeuvre) &&
                Objects.equals(nom_usager, that.nom_usager) &&
                Objects.equals(date_reservation, that.date_reservation);
    }

    @Override
    public int hashCode() {
        return Objects.hash(titre_oeuvre, nom_usager, date_reservation);
    }
}
