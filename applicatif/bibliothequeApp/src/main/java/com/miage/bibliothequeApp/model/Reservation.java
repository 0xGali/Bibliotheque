package com.miage.bibliothequeApp.model;

import java.util.Objects;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "Reservation")
@NoArgsConstructor
public class Reservation {

    @EmbeddedId
    private ReservationId id;

    public Reservation(ReservationId id) {
        this.id = id;
    }

    public void reserver(String titre, String nom) {
        // Logique de réservation
        if (this.id == null) {
            this.id = new ReservationId(titre, nom, new java.util.Date());
        }
    }

    public void emprunter(String titre, String nom) {
        // Annulation de la réservation lors de l'emprunt
        if (this.id != null && this.id.getTitreOeuvre().equals(titre) && this.id.getNomUsager().equals(nom)) {
            this.id = null;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Reservation that = (Reservation) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Reservation{" +
                "id=" + id +
                '}';
    }

    public Reservation(String titre, String nom) {
        this.id = new ReservationId(titre, nom, new java.util.Date());
    }
}
