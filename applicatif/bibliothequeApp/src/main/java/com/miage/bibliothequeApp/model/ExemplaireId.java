package com.miage.bibliothequeApp.model;

import java.io.Serializable;
import java.util.Objects;
import jakarta.persistence.Embeddable;

@Embeddable
public class ExemplaireId implements Serializable {
    private String titre_oeuvre;
    private Long num_exemplaire;

    // Constructeurs, getters, setters, equals, hashCode
    public ExemplaireId() {}

    public ExemplaireId(String titre_oeuvre, Long num_exemplaire) {
        this.titre_oeuvre = titre_oeuvre;
        this.num_exemplaire = num_exemplaire;
    }

    // Getters et setters

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ExemplaireId that = (ExemplaireId) o;
        return Objects.equals(titre_oeuvre, that.titre_oeuvre) &&
                Objects.equals(num_exemplaire, that.num_exemplaire);
    }

    @Override
    public int hashCode() {
        return Objects.hash(titre_oeuvre, num_exemplaire);
    }
}