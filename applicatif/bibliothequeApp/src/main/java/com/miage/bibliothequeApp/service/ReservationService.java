package com.miage.bibliothequeApp.service;

import com.miage.bibliothequeApp.model.Oeuvre;
import com.miage.bibliothequeApp.model.Reservation;
import com.miage.bibliothequeApp.model.ReservationId;
import com.miage.bibliothequeApp.model.Usager;
import com.miage.bibliothequeApp.repository.OeuvreRepository;
import com.miage.bibliothequeApp.repository.ReservationRepository;
import com.miage.bibliothequeApp.repository.UsagerRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
        Usager usager = usagerRepository.findById(reservationId.getNom_usager())
                .orElseThrow(() -> new RuntimeException("Usager non trouvé"));
        Oeuvre oeuvre = oeuvreRepository.findById(reservationId.getTitre_oeuvre())
                .orElseThrow(() -> new RuntimeException("Œuvre non trouvée"));


        reservationRepository.saveAndFlush(reservation);
        oeuvre.setNb_resa(oeuvre.getNb_resa() + 1);

        oeuvreRepository.save(oeuvre);

        return reservationId;
    }
}
