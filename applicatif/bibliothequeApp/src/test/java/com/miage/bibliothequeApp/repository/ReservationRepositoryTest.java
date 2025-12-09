package com.miage.bibliothequeApp.repository;

import com.miage.bibliothequeApp.model.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.TestPropertySource;

import java.util.Date;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@TestPropertySource(properties = {
    "spring.jpa.hibernate.ddl-auto=create-drop"
})
@DisplayName("Tests unitaires - ReservationRepository")
class ReservationRepositoryTest {

    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private TestEntityManager entityManager;

    @Test
    @DisplayName("Devrait sauvegarder une réservation")
    void save_avecReservationValide_devraitPersister() {
        // Arrange
        Usager usager = new Usager();
        usager.setNom("TestUsager");
        usager.setPrenom("Prenom");
        entityManager.persist(usager);

        Oeuvre oeuvre = new Oeuvre();
        oeuvre.setTitre("TestOeuvre");
        oeuvre.setAuteur("Auteur");
        oeuvre.setEditeur("Editeur");
        oeuvre.setEtat(EtatOeuvre.nonreservee);
        oeuvre.setNbResa(0);
        entityManager.persist(oeuvre);

        ReservationId id = new ReservationId("TestOeuvre", "TestUsager", new Date());
        Reservation reservation = new Reservation(id);

        // Act
        Reservation saved = reservationRepository.save(reservation);
        entityManager.flush();

        // Assert
        assertThat(saved).isNotNull();
        assertThat(saved.getId().getTitreOeuvre()).isEqualTo("TestOeuvre");
        assertThat(saved.getId().getNomUsager()).isEqualTo("TestUsager");
    }

    @Test
    @DisplayName("Devrait vérifier l'existence d'une réservation")
    void existsById_avecReservationExistante_devraitRetournerTrue() {
        // Arrange
        Usager usager = new Usager();
        usager.setNom("UsagerExist");
        usager.setPrenom("Prenom");
        entityManager.persist(usager);

        Oeuvre oeuvre = new Oeuvre();
        oeuvre.setTitre("OeuvreExist");
        oeuvre.setAuteur("Auteur");
        oeuvre.setEditeur("Editeur");
        oeuvre.setEtat(EtatOeuvre.nonreservee);
        oeuvre.setNbResa(0);
        entityManager.persist(oeuvre);

        ReservationId id = new ReservationId("OeuvreExist", "UsagerExist", new Date());
        Reservation reservation = new Reservation(id);
        entityManager.persist(reservation);
        entityManager.flush();

        // Act
        boolean exists = reservationRepository.existsById(id);

        // Assert
        assertThat(exists).isTrue();
    }

    @Test
    @DisplayName("Devrait retourner false pour réservation inexistante")
    void existsById_avecReservationInexistante_devraitRetournerFalse() {
        // Arrange
        ReservationId id = new ReservationId("OeuvreInexistante", "UsagerInexistant", new Date());

        // Act
        boolean exists = reservationRepository.existsById(id);

        // Assert
        assertThat(exists).isFalse();
    }

    @Test
    @DisplayName("Devrait trouver une réservation par son ID composite")
    void findById_avecIdValide_devraitRetournerReservation() {
        // Arrange
        Usager usager = new Usager();
        usager.setNom("UsagerFind");
        usager.setPrenom("Prenom");
        entityManager.persist(usager);

        Oeuvre oeuvre = new Oeuvre();
        oeuvre.setTitre("OeuvreFind");
        oeuvre.setAuteur("Auteur");
        oeuvre.setEditeur("Editeur");
        oeuvre.setEtat(EtatOeuvre.nonreservee);
        oeuvre.setNbResa(0);
        entityManager.persist(oeuvre);

        Date date = new Date();
        ReservationId id = new ReservationId("OeuvreFind", "UsagerFind", date);
        Reservation reservation = new Reservation(id);
        entityManager.persist(reservation);
        entityManager.flush();

        // Act
        Optional<Reservation> found = reservationRepository.findById(id);

        // Assert
        assertThat(found).isPresent();
        assertThat(found.get().getId().getTitreOeuvre()).isEqualTo("OeuvreFind");
        assertThat(found.get().getId().getNomUsager()).isEqualTo("UsagerFind");
    }

    @Test
    @DisplayName("Devrait supprimer une réservation")
    void delete_avecReservationExistante_devraitSupprimer() {
        // Arrange
        Usager usager = new Usager();
        usager.setNom("UsagerDelete");
        usager.setPrenom("Prenom");
        entityManager.persist(usager);

        Oeuvre oeuvre = new Oeuvre();
        oeuvre.setTitre("OeuvreDelete");
        oeuvre.setAuteur("Auteur");
        oeuvre.setEditeur("Editeur");
        oeuvre.setEtat(EtatOeuvre.nonreservee);
        oeuvre.setNbResa(0);
        entityManager.persist(oeuvre);

        ReservationId id = new ReservationId("OeuvreDelete", "UsagerDelete", new Date());
        Reservation reservation = new Reservation(id);
        entityManager.persist(reservation);
        entityManager.flush();

        // Act
        reservationRepository.delete(reservation);
        entityManager.flush();

        // Assert
        Optional<Reservation> found = reservationRepository.findById(id);
        assertThat(found).isEmpty();
    }
}
