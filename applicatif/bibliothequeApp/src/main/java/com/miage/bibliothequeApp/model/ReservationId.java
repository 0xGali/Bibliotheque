package com.miage.bibliothequeApp.model;

import java.util.Date;
import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Data;
import lombok.NoArgsConstructor;

@Embeddable
@Data
@NoArgsConstructor
public class ReservationId {

    @Column(name = "titre_oeuvre")
    private String titreOeuvre;
    @Column(name = "nom_usager")
    private String nomUsager;
    @Column(name = "date_reservation")
    private Date dateReservation;

    public ReservationId(String titreOeuvre, String nomUsager, Date dateReservation) {
        this.titreOeuvre = titreOeuvre;
        this.nomUsager = nomUsager;
        this.dateReservation = dateReservation;
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

    @Override
    public String toString() {
        return "ReservationId{" +
                "titreOeuvre='" + titreOeuvre + '\'' +
                ", nomUsager='" + nomUsager + '\'' +
                ", dateReservation=" + dateReservation +
                '}';
    }
}
