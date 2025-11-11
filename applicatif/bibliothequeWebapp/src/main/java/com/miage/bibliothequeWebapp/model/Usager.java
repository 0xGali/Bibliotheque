package com.miage.bibliothequeWebapp.model;


import lombok.Data;

import java.util.List;


@Data
public class Usager {

    private String nom;

    private String prenom;

    private List<Reservation> reservations;
}
