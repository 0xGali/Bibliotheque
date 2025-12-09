package com.miage.bibliothequeApp.model;

import java.util.List;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "Usager")
@NoArgsConstructor
public class Usager {

    @Id
    private String nom;

    private String prenom;

    @OneToMany(mappedBy = "id.nomUsager", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnoreProperties({"id.nomUsager"})
    private List<Reservation> reservations;

    public Usager(String nom, String prenom) {
        this.nom = nom;
        this.prenom = prenom;
    }

    public static Usager identification(String nom) {
        Usager u = new Usager();
        u.setNom(nom);
        return u;
    }

    public static void supprimerUsager(String nom) {
        // Logique de suppression gérée par le repository
    }

    public String getNom() {
        return nom;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Usager usager = (Usager) o;
        return Objects.equals(nom, usager.nom);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nom);
    }

    @Override
    public String toString() {
        return "Usager{" +
                "nom='" + nom + '\'' +
                ", prenom='" + prenom + '\'' +
                '}';
    }
}
