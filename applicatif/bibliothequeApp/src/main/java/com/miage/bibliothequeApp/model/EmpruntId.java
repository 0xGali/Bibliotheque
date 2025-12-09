package com.miage.bibliothequeApp.model;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

import jakarta.persistence.Embeddable;

@Embeddable
public class EmpruntId implements Serializable {
    private String nom;
    private Long numExemplaire;
    private Date dateEmprunt;

    // Constructeurs, getters, setters, equals, hashCode
    public EmpruntId() {}

    public EmpruntId(String nom, Long numExemplaire, Date dateEmprunt) {
        this.nom = nom;
        this.numExemplaire = numExemplaire;
        this.dateEmprunt = dateEmprunt;
    }

    // Getters et setters
    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public Long getNumExemplaire() {
        return numExemplaire;
    }

    public void setNumExemplaire(Long numExemplaire) {
        this.numExemplaire = numExemplaire;
    }

    public Date getDateEmprunt() {
        return dateEmprunt;
    }

    public void setDateEmprunt(Date dateEmprunt) {
        this.dateEmprunt = dateEmprunt;
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EmpruntId that = (EmpruntId) o;
        return Objects.equals(nom, that.nom) &&
                Objects.equals(numExemplaire, that.numExemplaire) &&
                Objects.equals(dateEmprunt, that.dateEmprunt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nom, numExemplaire, dateEmprunt);
    }
}