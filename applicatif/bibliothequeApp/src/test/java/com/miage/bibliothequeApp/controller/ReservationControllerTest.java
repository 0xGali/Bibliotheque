package com.miage.bibliothequeApp.controller;

import com.miage.bibliothequeApp.TestBase;
import com.miage.bibliothequeApp.model.*;
import com.miage.bibliothequeApp.repository.OeuvreRepository;
import com.miage.bibliothequeApp.repository.ReservationRepository;
import com.miage.bibliothequeApp.repository.UsagerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;

import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@DisplayName("Tests d'intégration - ReservationController")
class ReservationControllerTest extends TestBase {

    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private UsagerRepository usagerRepository;

    @Autowired
    private OeuvreRepository oeuvreRepository;

    @BeforeEach
    void setUp() {
        reservationRepository.deleteAll();
        usagerRepository.deleteAll();
        oeuvreRepository.deleteAll();
    }

    @Test
    @DisplayName("POST /faireUneReservation - Devrait créer réservation avec succès")
    void faireUneReservation_avecDonneesValides_devraitCreer() throws Exception {
        // Arrange
        Usager usager = new Usager();
        usager.setNom("Dupont");
        usager.setPrenom("Jean");
        usagerRepository.save(usager);

        Oeuvre oeuvre = new Oeuvre();
        oeuvre.setTitre("Le Seigneur des Anneaux");
        oeuvre.setAuteur("Tolkien");
        oeuvre.setEditeur("Gallimard");
        oeuvre.setEtat(EtatOeuvre.nonreservee);
        oeuvre.setNbResa(0);
        oeuvreRepository.save(oeuvre);

        ReservationId reservationId = new ReservationId(
            "Le Seigneur des Anneaux",
            "Dupont",
            new Date()
        );

        // Act & Assert
        mockMvc.perform(post("/faireUneReservation")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(reservationId)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.titreOeuvre", is("Le Seigneur des Anneaux")))
                .andExpect(jsonPath("$.nomUsager", is("Dupont")))
                .andExpect(jsonPath("$.dateReservation").exists());

        // Vérifier que la réservation existe en base
        boolean exists = reservationRepository.existsById(reservationId);
        assertThat(exists).isTrue();
    }

    @Test
    @DisplayName("POST /faireUneReservation - Devrait incrémenter nbResa de l'œuvre")
    void faireUneReservation_devraitIncrementerNbResa() throws Exception {
        // Arrange
        Usager usager = new Usager();
        usager.setNom("Martin");
        usager.setPrenom("Marie");
        usagerRepository.save(usager);

        Oeuvre oeuvre = new Oeuvre();
        oeuvre.setTitre("Harry Potter");
        oeuvre.setAuteur("Rowling");
        oeuvre.setEditeur("Gallimard");
        oeuvre.setEtat(EtatOeuvre.nonreservee);
        oeuvre.setNbResa(0);
        oeuvreRepository.save(oeuvre);

        ReservationId reservationId = new ReservationId(
            "Harry Potter",
            "Martin",
            new Date()
        );

        // Act
        mockMvc.perform(post("/faireUneReservation")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(reservationId)))
                .andExpect(status().isOk());

        // Assert
        Oeuvre oeuvreApres = oeuvreRepository.findById("Harry Potter").orElse(null);
        assertThat(oeuvreApres).isNotNull();
        assertThat(oeuvreApres.getNbResa()).isEqualTo(1);
        assertThat(oeuvreApres.getEtat()).isEqualTo(EtatOeuvre.reservee);
    }

    @Test
    @DisplayName("POST /faireUneReservation - Devrait changer état en 'reservee'")
    void faireUneReservation_devraitChangerEtatEnReservee() throws Exception {
        // Arrange
        Usager usager = new Usager();
        usager.setNom("Durand");
        usager.setPrenom("Pierre");
        usagerRepository.save(usager);

        Oeuvre oeuvre = new Oeuvre();
        oeuvre.setTitre("1984");
        oeuvre.setAuteur("Orwell");
        oeuvre.setEditeur("Gallimard");
        oeuvre.setEtat(EtatOeuvre.nonreservee);
        oeuvre.setNbResa(0);
        oeuvreRepository.save(oeuvre);

        ReservationId reservationId = new ReservationId("1984", "Durand", new Date());

        // Act
        mockMvc.perform(post("/faireUneReservation")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(reservationId)))
                .andExpect(status().isOk());

        // Assert
        Oeuvre oeuvreApres = oeuvreRepository.findById("1984").orElse(null);
        assertThat(oeuvreApres.getEtat()).isEqualTo(EtatOeuvre.reservee);
    }

    @Test
    @DisplayName("POST /faireUneReservation - Devrait échouer si réservation existe déjà")
    void faireUneReservation_avecReservationExistante_devraitEchouer() throws Exception {
        // Arrange
        Usager usager = new Usager();
        usager.setNom("Lefebvre");
        usager.setPrenom("Sophie");
        usagerRepository.save(usager);

        Oeuvre oeuvre = new Oeuvre();
        oeuvre.setTitre("Le Petit Prince");
        oeuvre.setAuteur("Saint-Exupéry");
        oeuvre.setEditeur("Gallimard");
        oeuvre.setEtat(EtatOeuvre.nonreservee);
        oeuvre.setNbResa(0);
        oeuvreRepository.save(oeuvre);

        ReservationId reservationId = new ReservationId("Le Petit Prince", "Lefebvre", new Date());
        Reservation reservation = new Reservation(reservationId);
        reservationRepository.save(reservation);

        // Act & Assert
        mockMvc.perform(post("/faireUneReservation")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(reservationId)))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message", containsString("Une réservation existe déjà")));
    }

    @Test
    @DisplayName("POST /faireUneReservation - Devrait échouer si usager inexistant")
    void faireUneReservation_avecUsagerInexistant_devraitEchouer() throws Exception {
        // Arrange
        Oeuvre oeuvre = new Oeuvre();
        oeuvre.setTitre("Test Oeuvre");
        oeuvre.setAuteur("Auteur");
        oeuvre.setEditeur("Editeur");
        oeuvre.setEtat(EtatOeuvre.nonreservee);
        oeuvre.setNbResa(0);
        oeuvreRepository.save(oeuvre);

        ReservationId reservationId = new ReservationId("Test Oeuvre", "UsagerInexistant", new Date());

        // Act & Assert
        mockMvc.perform(post("/faireUneReservation")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(reservationId)))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message", containsString("Usager non trouvé")));
    }

    @Test
    @DisplayName("POST /faireUneReservation - Devrait échouer si œuvre inexistante")
    void faireUneReservation_avecOeuvreInexistante_devraitEchouer() throws Exception {
        // Arrange
        Usager usager = new Usager();
        usager.setNom("Test");
        usager.setPrenom("User");
        usagerRepository.save(usager);

        ReservationId reservationId = new ReservationId("OeuvreInexistante", "Test", new Date());

        // Act & Assert
        mockMvc.perform(post("/faireUneReservation")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(reservationId)))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message", containsString("Œuvre non trouvée")));
    }

    @Test
    @DisplayName("POST /faireUneReservation - Multiples réservations sur même œuvre")
    void faireUneReservation_multiplesReservations_devraitIncrementerNbResa() throws Exception {
        // Arrange
        Usager usager1 = new Usager();
        usager1.setNom("Usager1");
        usager1.setPrenom("Premier");
        usagerRepository.save(usager1);

        Usager usager2 = new Usager();
        usager2.setNom("Usager2");
        usager2.setPrenom("Deuxieme");
        usagerRepository.save(usager2);

        Oeuvre oeuvre = new Oeuvre();
        oeuvre.setTitre("Oeuvre Populaire");
        oeuvre.setAuteur("Auteur");
        oeuvre.setEditeur("Editeur");
        oeuvre.setEtat(EtatOeuvre.nonreservee);
        oeuvre.setNbResa(0);
        oeuvreRepository.save(oeuvre);

        // Act - Première réservation
        ReservationId resa1 = new ReservationId("Oeuvre Populaire", "Usager1", new Date());
        mockMvc.perform(post("/faireUneReservation")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(resa1)))
                .andExpect(status().isOk());

        // Act - Deuxième réservation
        Thread.sleep(10); // Assurer des dates différentes
        ReservationId resa2 = new ReservationId("Oeuvre Populaire", "Usager2", new Date());
        mockMvc.perform(post("/faireUneReservation")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(resa2)))
                .andExpect(status().isOk());

        // Assert
        Oeuvre oeuvreApres = oeuvreRepository.findById("Oeuvre Populaire").orElse(null);
        assertThat(oeuvreApres.getNbResa()).isEqualTo(2);
        assertThat(oeuvreApres.getEtat()).isEqualTo(EtatOeuvre.reservee);
    }
}
