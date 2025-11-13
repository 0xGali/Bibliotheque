package com.miage.bibliothequeApp.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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
    @JsonIgnoreProperties({"id.nom_usager"})
    private List<Reservation> reservations;
}
