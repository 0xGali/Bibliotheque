package com.miage.bibliothequeApp.model;

import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "Emprunt")
@NoArgsConstructor
public class Emprunt {
    @EmbeddedId
    private EmpruntId id;

    @Column(name = "titre_oeuvre_emprunte")
    private String titreOeuvre;

    public Emprunt(EmpruntId id, String titreOeuvre) {
        this.id = id;
        this.titreOeuvre = titreOeuvre;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Emprunt emprunt = (Emprunt) o;
        return Objects.equals(id, emprunt.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Emprunt{" +
                "id=" + id +
                ", titreOeuvre='" + titreOeuvre + '\'' +
                '}';
    }
}