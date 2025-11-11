package com.miage.bibliothequeApp.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;
import java.util.Set;

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

    @OneToMany(mappedBy = "id.titre_oeuvre", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Reservation> reservations;
}
