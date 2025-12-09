package com.miage.bibliothequeApp.controller;

import com.miage.bibliothequeApp.TestBase;
import com.miage.bibliothequeApp.model.EtatOeuvre;
import com.miage.bibliothequeApp.model.Oeuvre;
import com.miage.bibliothequeApp.repository.OeuvreRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@DisplayName("Tests d'intégration - OeuvreController")
class OeuvreControllerTest extends TestBase {

    @Autowired
    private OeuvreRepository oeuvreRepository;

    @BeforeEach
    void setUp() {
        oeuvreRepository.deleteAll();
    }

    @Test
    @DisplayName("GET /getOeuvres - Devrait retourner liste vide")
    void getOeuvres_sansOeuvres_devraitRetournerListeVide() throws Exception {
        mockMvc.perform(get("/getOeuvres")
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(0)));
    }

    @Test
    @DisplayName("GET /getOeuvres - Devrait retourner toutes les œuvres")
    void getOeuvres_avecOeuvres_devraitRetournerListe() throws Exception {
        // Arrange
        Oeuvre oeuvre1 = new Oeuvre();
        oeuvre1.setTitre("Le Seigneur des Anneaux");
        oeuvre1.setAuteur("Tolkien");
        oeuvre1.setEditeur("Gallimard");
        oeuvre1.setEtat(EtatOeuvre.nonreservee);
        oeuvre1.setNbResa(0);

        Oeuvre oeuvre2 = new Oeuvre();
        oeuvre2.setTitre("Harry Potter");
        oeuvre2.setAuteur("Rowling");
        oeuvre2.setEditeur("Gallimard");
        oeuvre2.setEtat(EtatOeuvre.reservee);
        oeuvre2.setNbResa(2);

        oeuvreRepository.save(oeuvre1);
        oeuvreRepository.save(oeuvre2);

        // Act & Assert
        mockMvc.perform(get("/getOeuvres")
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[*].titre", containsInAnyOrder("Le Seigneur des Anneaux", "Harry Potter")))
                .andExpect(jsonPath("$[*].auteur", containsInAnyOrder("Tolkien", "Rowling")));
    }

    @Test
    @DisplayName("GET /oeuvre/{titre} - Devrait retourner une œuvre")
    void getOeuvre_avecTitreExistant_devraitRetournerOeuvre() throws Exception {
        // Arrange
        Oeuvre oeuvre = new Oeuvre();
        oeuvre.setTitre("1984");
        oeuvre.setAuteur("George Orwell");
        oeuvre.setEditeur("Gallimard");
        oeuvre.setEtat(EtatOeuvre.nonreservee);
        oeuvre.setNbResa(0);
        oeuvreRepository.save(oeuvre);

        // Act & Assert
        mockMvc.perform(get("/oeuvre/{titre}", "1984")
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.titre", is("1984")))
                .andExpect(jsonPath("$.auteur", is("George Orwell")))
                .andExpect(jsonPath("$.editeur", is("Gallimard")))
                .andExpect(jsonPath("$.etat", is("nonreservee")))
                .andExpect(jsonPath("$.nbResa", is(0)));
    }

    @Test
    @DisplayName("GET /oeuvre/{titre} - Devrait retourner null si inexistant")
    void getOeuvre_avecTitreInexistant_devraitRetournerNull() throws Exception {
        mockMvc.perform(get("/oeuvre/{titre}", "TitreInexistant")
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(""));
    }

    @Test
    @DisplayName("POST /addOeuvre - Devrait créer une nouvelle œuvre")
    void addOeuvre_avecOeuvreValide_devraitCreer() throws Exception {
        // Arrange
        Oeuvre nouvelleOeuvre = new Oeuvre();
        nouvelleOeuvre.setTitre("Le Petit Prince");
        nouvelleOeuvre.setAuteur("Saint-Exupéry");
        nouvelleOeuvre.setEditeur("Gallimard");
        nouvelleOeuvre.setEtat(EtatOeuvre.nonreservee);
        nouvelleOeuvre.setNbResa(0);

        // Act & Assert
        MvcResult result = mockMvc.perform(post("/addOeuvre")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(nouvelleOeuvre)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.titre", is("Le Petit Prince")))
                .andExpect(jsonPath("$.auteur", is("Saint-Exupéry")))
                .andReturn();

        // Vérifier en base de données
        Oeuvre savedOeuvre = oeuvreRepository.findById("Le Petit Prince").orElse(null);
        assertThat(savedOeuvre).isNotNull();
        assertThat(savedOeuvre.getAuteur()).isEqualTo("Saint-Exupéry");
    }

    @Test
    @DisplayName("POST /addOeuvre - Devrait persister l'état de l'œuvre")
    void addOeuvre_avecEtatReservee_devraitPersisterEtat() throws Exception {
        // Arrange
        Oeuvre oeuvre = new Oeuvre();
        oeuvre.setTitre("Test Etat");
        oeuvre.setAuteur("Auteur Test");
        oeuvre.setEditeur("Editeur Test");
        oeuvre.setEtat(EtatOeuvre.reservee);
        oeuvre.setNbResa(3);

        // Act
        mockMvc.perform(post("/addOeuvre")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(oeuvre)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.etat", is("reservee")))
                .andExpect(jsonPath("$.nbResa", is(3)));

        // Assert
        Oeuvre savedOeuvre = oeuvreRepository.findById("Test Etat").orElse(null);
        assertThat(savedOeuvre).isNotNull();
        assertThat(savedOeuvre.getEtat()).isEqualTo(EtatOeuvre.reservee);
        assertThat(savedOeuvre.getNbResa()).isEqualTo(3);
    }

    @Test
    @DisplayName("POST /addOeuvre - Devrait mettre à jour œuvre existante")
    void addOeuvre_avecOeuvreExistante_devraitMettreAJour() throws Exception {
        // Arrange - Créer œuvre initiale
        Oeuvre oeuvreInitiale = new Oeuvre();
        oeuvreInitiale.setTitre("Titre Existant");
        oeuvreInitiale.setAuteur("Auteur Original");
        oeuvreInitiale.setEditeur("Editeur Original");
        oeuvreInitiale.setEtat(EtatOeuvre.nonreservee);
        oeuvreInitiale.setNbResa(0);
        oeuvreRepository.save(oeuvreInitiale);

        // Modifier l'œuvre
        Oeuvre oeuvreModifiee = new Oeuvre();
        oeuvreModifiee.setTitre("Titre Existant");
        oeuvreModifiee.setAuteur("Auteur Modifié");
        oeuvreModifiee.setEditeur("Editeur Modifié");
        oeuvreModifiee.setEtat(EtatOeuvre.reservee);
        oeuvreModifiee.setNbResa(5);

        // Act & Assert
        mockMvc.perform(post("/addOeuvre")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(oeuvreModifiee)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.auteur", is("Auteur Modifié")))
                .andExpect(jsonPath("$.nbResa", is(5)));

        // Vérifier qu'il n'y a qu'une seule œuvre
        assertThat(oeuvreRepository.count()).isEqualTo(1);
    }
}
