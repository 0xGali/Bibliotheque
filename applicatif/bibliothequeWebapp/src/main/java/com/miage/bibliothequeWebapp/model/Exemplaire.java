package com.miage.bibliothequeWebapp.model;

import lombok.Data;

@Data
public class Exemplaire {

    private ExemplaireId id;
    private EtatExemplaire etat;

    // Getters et setters
}