package com.miage.bibliothequeApp.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "Oeuvre")
public class Oeuvre {
    @Id
    private String titre;
    private String auteur;
    private String editeur;

    @Enumerated(EnumType.STRING)
    @Column(name = "etat")
    private EtatOeuvre etat;
    
    private Integer nbresa;
}
