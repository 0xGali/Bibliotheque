package com.miage.bibliothequeApp.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name="Oeuvre")
public class Oeuvre {
    @Id
    private String titre;
    private String auteur;
    private String editeur;
}
