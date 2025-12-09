package com.miage.bibliothequeWebapp.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

@Data
public class Exemplaire {

    private Integer numExemplaire;
    private String titreOeuvre;
    private EtatExemplaire etat;
    
}