package com.miage.bibliothequeApp.model;

import java.io.Serializable;
import java.util.Objects;
import jakarta.persistence.Embeddable;

@Embeddable
public class ExemplaireId implements Serializable {
    private String titre;
    private Long numExemplaire;

    // Constructeurs, getters, setters, equals, hashCode
    public ExemplaireId() {}

    public ExemplaireId(String titre, Long numExemplaire) {
        this.titre = titre;
        this.numExemplaire = numExemplaire;
    }

    // Getters et setters

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ExemplaireId that = (ExemplaireId) o;
        return Objects.equals(titre, that.titre) &&
                Objects.equals(numExemplaire, that.numExemplaire);
    }

    @Override
    public int hashCode() {
        return Objects.hash(titre, numExemplaire);
    }
}