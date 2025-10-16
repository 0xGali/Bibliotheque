package com.miage.bibliothequeWebapp.model;

import java.io.Serializable;
import java.util.Objects;


public class ExemplaireId implements Serializable {
    private String titre;
    private Long numexemplaire;

    // Constructeurs, getters, setters, equals, hashCode
    public ExemplaireId() {}

    public ExemplaireId(String titre, Long numExemplaire) {
        this.titre = titre;
        this.numexemplaire = numExemplaire;
    }

    // Getters et setters

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ExemplaireId that = (ExemplaireId) o;
        return Objects.equals(titre, that.titre) &&
                Objects.equals(numexemplaire, that.numexemplaire);
    }

    @Override
    public int hashCode() {
        return Objects.hash(titre, numexemplaire);
    }
}