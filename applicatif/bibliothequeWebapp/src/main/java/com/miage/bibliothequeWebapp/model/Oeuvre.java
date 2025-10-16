package com.miage.bibliothequeWebapp.model;

import lombok.Data;

@Data
public class Oeuvre {

    private String titre;
    private String auteur;
    private String editeur;

    private EtatOeuvre etat;
    
    private Integer nbresa;
}
