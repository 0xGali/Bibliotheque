package com.miage.bibliothequeApp.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "Usager")
public class Usager {

    @Id
    private String nom;

    private String prenom;
}
