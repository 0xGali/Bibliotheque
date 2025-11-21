package com.miage.bibliothequeWebapp.model;

import lombok.Data;

import java.util.List;

@Data
public class Oeuvre {

    private String titre;
    private String auteur;
    private String editeur;

    private EtatOeuvre etat;
    
    private Integer nb_resa;

    private List<Reservation> reservations;
}
