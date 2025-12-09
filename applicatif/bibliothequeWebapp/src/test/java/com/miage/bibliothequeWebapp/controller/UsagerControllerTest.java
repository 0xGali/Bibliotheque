package com.miage.bibliothequeWebapp.controller;

import com.miage.bibliothequeWebapp.model.Oeuvre;
import com.miage.bibliothequeWebapp.model.ReservationId;
import com.miage.bibliothequeWebapp.model.Usager;
import com.miage.bibliothequeWebapp.service.OeuvreService;
import com.miage.bibliothequeWebapp.service.UsagerService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UsagerController.class)
@DisplayName("Tests unitaires - UsagerController")
class UsagerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UsagerService usagerService;

    @MockBean
    private OeuvreService oeuvreService;

    @Test
    @DisplayName("GET /voirLesUsagers - Devrait afficher la liste des usagers")
    void readUsagers_devraitAfficherListeUsagers() throws Exception {
        // Arrange
        Usager usager1 = new Usager();
        usager1.setNom("Dupont");
        usager1.setPrenom("Jean");

        Usager usager2 = new Usager();
        usager2.setNom("Martin");
        usager2.setPrenom("Marie");

        List<Usager> usagers = Arrays.asList(usager1, usager2);
        when(usagerService.getUsagers()).thenReturn(usagers);

        // Act & Assert
        mockMvc.perform(get("/voirLesUsagers"))
                .andExpect(status().isOk())
                .andExpect(view().name("usagers"))
                .andExpect(model().attributeExists("usagers"))
                .andExpect(model().attribute("usagers", hasSize(2)));

        verify(usagerService, times(1)).getUsagers();
    }

    @Test
    @DisplayName("GET /voirUnUsager/{nom} - Devrait afficher le détail avec formulaire réservation")
    void readUsager_avecNomValide_devraitAfficherDetailEtFormulaire() throws Exception {
        // Arrange
        Usager usager = new Usager();
        usager.setNom("Dupont");
        usager.setPrenom("Jean");
        usager.setReservations(new ArrayList<>()); // Initialiser la liste des réservations

        when(usagerService.getUsager("Dupont")).thenReturn(usager);
        when(oeuvreService.getOeuvres()).thenReturn(List.of());

        // Act & Assert
        mockMvc.perform(get("/voirUnUsager/{nom}", "Dupont"))
                .andExpect(status().isOk())
                .andExpect(view().name("usager"))
                .andExpect(model().attributeExists("usager"))
                .andExpect(model().attributeExists("oeuvres"))
                .andExpect(model().attributeExists("reservation"))
                .andExpect(model().attribute("usager", hasProperty("nom", is("Dupont"))))
                .andExpect(model().attribute("reservation", instanceOf(ReservationId.class)));

        verify(usagerService, times(1)).getUsager("Dupont");
        verify(oeuvreService, times(1)).getOeuvres();
    }

    @Test
    @DisplayName("GET /formUsager - Devrait afficher le formulaire")
    void createUsager_devraitAfficherFormulaire() throws Exception {
        mockMvc.perform(get("/formUsager"))
                .andExpect(status().isOk())
                .andExpect(view().name("formNewUsager"))
                .andExpect(model().attributeExists("usager"))
                .andExpect(model().attribute("usager", instanceOf(Usager.class)));
    }

    @Test
    @DisplayName("POST /addUsager - Devrait sauvegarder et rediriger")
    void saveUsager_avecUsagerValide_devraitSauvegarderEtRediriger() throws Exception {
        // Arrange
        Usager usager = new Usager();
        usager.setNom("Durand");
        usager.setPrenom("Pierre");

        when(usagerService.saveUsager(any(Usager.class))).thenReturn(usager);

        // Act & Assert
        mockMvc.perform(post("/addUsager")
                .param("nom", "Durand")
                .param("prenom", "Pierre"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/voirLesUsagers"));

        verify(usagerService, times(1)).saveUsager(any(Usager.class));
    }
}
