package com.miage.bibliothequeApp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.miage.bibliothequeApp.model.EtatOeuvre;
import com.miage.bibliothequeApp.model.Oeuvre;
import com.miage.bibliothequeApp.model.Reservation;
import com.miage.bibliothequeApp.model.ReservationId;
import com.miage.bibliothequeApp.model.Usager;
import com.miage.bibliothequeApp.repository.OeuvreRepository;
import com.miage.bibliothequeApp.repository.ReservationRepository;
import com.miage.bibliothequeApp.repository.UsagerRepository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@Service
public class ReservationService {

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private UsagerRepository usagerRepository;

    @Autowired
    private OeuvreRepository oeuvreRepository;
    @Autowired
    private UsagerService usagerService;
    @Autowired
    private ReservationRepository reservationRepository;

    public ReservationId faireUneReservation(ReservationId reservationId) {
        //Vérifier que la réservation n'existe pas déjà
        if (reservationRepository.existsById(reservationId)) {
            throw new RuntimeException("Une réservation existe déjà pour cet usager et cette œuvre.");
        }

        entityManager.clear();

        //Créer la nouvelle réservation
        Reservation reservation = new Reservation(reservationId);

        //Récupérer Usager et Oeuvre
        Usager usager = usagerRepository.findById(reservationId.getNomUsager())
                .orElseThrow(() -> new RuntimeException("Usager non trouvé"));
        Oeuvre oeuvre = oeuvreRepository.findById(reservationId.getTitreOeuvre())
                .orElseThrow(() -> new RuntimeException("Œuvre non trouvée"));


        reservationRepository.saveAndFlush(reservation);
        oeuvre.setNbResa(oeuvre.getNbResa() + 1);
        oeuvre.setEtat(EtatOeuvre.reservee);

        oeuvreRepository.save(oeuvre);

        return reservationId;
    }
}
