package com.miage.bibliothequeWebapp.model;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

import lombok.Data;

@Data
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

    public String getDateEmpruntFormatee() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        return sdf.format(this.dateEmprunt);
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