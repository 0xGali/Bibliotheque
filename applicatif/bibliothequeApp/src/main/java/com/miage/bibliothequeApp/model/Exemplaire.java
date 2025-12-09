package com.miage.bibliothequeApp.model;

import java.util.Objects;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "Exemplaire")
@NoArgsConstructor
public class Exemplaire {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer numExemplaire;

    private String titreOeuvre;

    @Enumerated(EnumType.STRING)
    private EtatExemplaire etat;

    public Exemplaire(Integer numExemplaire, String titreOeuvre, EtatExemplaire etat) {
        this.numExemplaire = numExemplaire;
        this.titreOeuvre = titreOeuvre;
        this.etat = etat;
    }

    public void modifierEtat(EtatExemplaire nouvelEtat) {
        this.etat = nouvelEtat;
    }

    public static Exemplaire identification(Integer numExemplaire) {
        Exemplaire ex = new Exemplaire();
        ex.setNumExemplaire(numExemplaire);
        return ex;
    }

    public static void supprimerExemplaire(Integer numExemplaire) {
        // Logique de suppression gérée par le repository
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Exemplaire that = (Exemplaire) o;
        return Objects.equals(numExemplaire, that.numExemplaire);
    }

    @Override
    public int hashCode() {
        return Objects.hash(numExemplaire);
    }

    @Override
    public String toString() {
        return "Exemplaire{" +
                "numExemplaire=" + numExemplaire +
                ", titreOeuvre='" + titreOeuvre + '\'' +
                ", etat=" + etat +
                '}';
    }
}