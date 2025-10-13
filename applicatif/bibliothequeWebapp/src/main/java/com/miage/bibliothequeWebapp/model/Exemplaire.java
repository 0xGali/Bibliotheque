package com.miage.bibliothequeApp.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "Exemplaire")
public class Exemplaire {
    @EmbeddedId
    private ExemplaireId id;

    @Enumerated(EnumType.STRING)
    private EtatExemplaire etat;

    // Getters et setters
}