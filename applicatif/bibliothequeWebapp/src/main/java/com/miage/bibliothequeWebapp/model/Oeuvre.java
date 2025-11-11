package com.miage.bibliothequeWebapp.model;

import lombok.Data;

import java.util.List;
import java.util.Set;

@Data
public class Oeuvre {

    private String titre;
    private String auteur;
    private String editeur;

    private EtatOeuvre etat;
    
    private Integer nbresa;

    private List<Reservation> reservations;
}
