package com.miage.bibliothequeApp.controller;

import com.miage.bibliothequeApp.TestBase;
import com.miage.bibliothequeApp.model.Usager;
import com.miage.bibliothequeApp.repository.UsagerRepository;
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

@DisplayName("Tests d'intégration - UsagerController")
class UsagerControllerTest extends TestBase {

    @Autowired
    private UsagerRepository usagerRepository;

    @BeforeEach
    void setUp() {
        usagerRepository.deleteAll();
    }

    @Test
    @DisplayName("GET /getUsagers - Devrait retourner liste vide")
    void getUsagers_sansUsagers_devraitRetournerListeVide() throws Exception {
        mockMvc.perform(get("/getUsagers")
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(0)));
    }

    @Test
    @DisplayName("GET /getUsagers - Devrait retourner tous les usagers")
    void getUsagers_avecUsagers_devraitRetournerListe() throws Exception {
        // Arrange
        Usager usager1 = new Usager();
        usager1.setNom("Dupont");
        usager1.setPrenom("Jean");

        Usager usager2 = new Usager();
        usager2.setNom("Martin");
        usager2.setPrenom("Marie");

        usagerRepository.save(usager1);
        usagerRepository.save(usager2);

        // Act & Assert
        mockMvc.perform(get("/getUsagers")
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[*].nom", containsInAnyOrder("Dupont", "Martin")))
                .andExpect(jsonPath("$[*].prenom", containsInAnyOrder("Jean", "Marie")));
    }

    @Test
    @DisplayName("GET /usager/{nom} - Devrait retourner un usager")
    void getUsager_avecNomExistant_devraitRetournerUsager() throws Exception {
        // Arrange
        Usager usager = new Usager();
        usager.setNom("Durand");
        usager.setPrenom("Pierre");
        usagerRepository.save(usager);

        // Act & Assert
        mockMvc.perform(get("/usager/{nom}", "Durand")
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.nom", is("Durand")))
                .andExpect(jsonPath("$.prenom", is("Pierre")));
    }

    @Test
    @DisplayName("GET /usager/{nom} - Devrait retourner null si inexistant")
    void getUsager_avecNomInexistant_devraitRetournerNull() throws Exception {
        mockMvc.perform(get("/usager/{nom}", "NomInexistant")
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(""));
    }

    @Test
    @DisplayName("POST /addUsager - Devrait créer un nouvel usager")
    void addUsager_avecUsagerValide_devraitCreer() throws Exception {
        // Arrange
        Usager nouvelUsager = new Usager();
        nouvelUsager.setNom("Lefebvre");
        nouvelUsager.setPrenom("Sophie");

        // Act & Assert
        MvcResult result = mockMvc.perform(post("/addUsager")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(nouvelUsager)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.nom", is("Lefebvre")))
                .andExpect(jsonPath("$.prenom", is("Sophie")))
                .andReturn();

        // Vérifier en base de données
        Usager savedUsager = usagerRepository.findById("Lefebvre").orElse(null);
        assertThat(savedUsager).isNotNull();
        assertThat(savedUsager.getPrenom()).isEqualTo("Sophie");
    }

    @Test
    @DisplayName("POST /addUsager - Devrait mettre à jour usager existant")
    void addUsager_avecUsagerExistant_devraitMettreAJour() throws Exception {
        // Arrange
        Usager usagerInitial = new Usager();
        usagerInitial.setNom("Dubois");
        usagerInitial.setPrenom("Ancien");
        usagerRepository.save(usagerInitial);

        Usager usagerModifie = new Usager();
        usagerModifie.setNom("Dubois");
        usagerModifie.setPrenom("Nouveau");

        // Act & Assert
        mockMvc.perform(post("/addUsager")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(usagerModifie)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.prenom", is("Nouveau")));

        // Vérifier qu'il n'y a qu'un seul usager
        assertThat(usagerRepository.count()).isEqualTo(1);
        Usager updated = usagerRepository.findById("Dubois").orElse(null);
        assertThat(updated.getPrenom()).isEqualTo("Nouveau");
    }

    @Test
    @DisplayName("GET /usager/{nom} - Devrait retourner usager avec réservations")
    void getUsager_avecReservations_devraitInclureReservations() throws Exception {
        // Arrange
        Usager usager = new Usager();
        usager.setNom("AvecResa");
        usager.setPrenom("Test");
        usagerRepository.save(usager);

        // Act & Assert
        mockMvc.perform(get("/usager/{nom}", "AvecResa")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nom", is("AvecResa")))
                .andExpect(jsonPath("$.reservations").exists());
    }
}
