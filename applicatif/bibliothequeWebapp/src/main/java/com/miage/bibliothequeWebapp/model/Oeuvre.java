package com.miage.bibliothequeWebapp.model;

import java.util.List;

import lombok.Data;

@Data
public class Oeuvre {

    private String titre;
    private String auteur;
    private String editeur;

    private EtatOeuvre etat;
    
    private Integer nbResa;

    private List<Reservation> reservations;
}
