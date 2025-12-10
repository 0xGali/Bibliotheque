package com.miage.bibliothequeApp.model;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "Oeuvre")
public class Oeuvre {
    @Id
    @Column(name = "titre")
    private String titre;
    @Column(name = "auteur")
    private String auteur;
    @Column(name = "editeur")
    private String editeur;

    @Enumerated(EnumType.STRING)
    @Column(name = "etat")
    private EtatOeuvre etat;

    @Column(name = "nb_resa")
    private Integer nbResa;

    @OneToMany(mappedBy = "id.titreOeuvre", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Reservation> reservations;
}
