package com.miage.bibliothequeApp.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;
import java.util.Set;

@Data
@Entity
@Table(name = "Usager")
public class Usager {

    @Id
    private String nom;

    private String prenom;

    @OneToMany(mappedBy = "id.nom_usager", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Reservation> reservations;
}
