package com.miage.bibliothequeWebapp.model;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

import lombok.Data;

@Data
public class ReservationId {

    private String titreOeuvre;
    private String nomUsager;
    private Date dateReservation;

    public ReservationId() {}

    public ReservationId(String titreOeuvre, String nomUsager, Date dateReservation) {
        this.titreOeuvre = titreOeuvre;
        this.nomUsager = nomUsager;
        this.dateReservation = dateReservation;
    }


    public String getdateReservation_formatee() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        return sdf.format(this.dateReservation);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ReservationId that = (ReservationId) o;
        return Objects.equals(titreOeuvre, that.titreOeuvre) &&
                Objects.equals(nomUsager, that.nomUsager) &&
                Objects.equals(dateReservation, that.dateReservation);
    }

    @Override
    public int hashCode() {
        return Objects.hash(titreOeuvre, nomUsager, dateReservation);
    }
}
