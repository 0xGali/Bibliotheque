package com.miage.bibliothequeWebapp.controller;

import com.miage.bibliothequeWebapp.model.EtatOeuvre;
import com.miage.bibliothequeWebapp.model.Oeuvre;
import com.miage.bibliothequeWebapp.service.OeuvreService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(OeuvreController.class)
@DisplayName("Tests unitaires - OeuvreController")
class OeuvreControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private OeuvreService oeuvreService;

    @Test
    @DisplayName("GET /voirLesOeuvres - Devrait afficher la liste des œuvres")
    void readOeuvres_devraitAfficherListeOeuvres() throws Exception {
        // Arrange
        Oeuvre oeuvre1 = new Oeuvre();
        oeuvre1.setTitre("Le Seigneur des Anneaux");
        oeuvre1.setAuteur("Tolkien");
        oeuvre1.setEditeur("Gallimard");

        Oeuvre oeuvre2 = new Oeuvre();
        oeuvre2.setTitre("Harry Potter");
        oeuvre2.setAuteur("Rowling");
        oeuvre2.setEditeur("Gallimard");

        List<Oeuvre> oeuvres = Arrays.asList(oeuvre1, oeuvre2);
        when(oeuvreService.getOeuvres()).thenReturn(oeuvres);

        // Act & Assert
        mockMvc.perform(get("/voirLesOeuvres"))
                .andExpect(status().isOk())
                .andExpect(view().name("oeuvres"))
                .andExpect(model().attributeExists("oeuvres"))
                .andExpect(model().attribute("oeuvres", hasSize(2)))
                .andExpect(model().attribute("oeuvres", hasItem(
                    hasProperty("titre", is("Le Seigneur des Anneaux"))
                )));

        verify(oeuvreService, times(1)).getOeuvres();
    }

    @Test
    @DisplayName("GET /voirUneOeuvre/{titre} - Devrait afficher le détail d'une œuvre")
    void readOeuvre_avecTitreValide_devraitAfficherDetail() throws Exception {
        // Arrange
        Oeuvre oeuvre = new Oeuvre();
        oeuvre.setTitre("1984");
        oeuvre.setAuteur("George Orwell");
        oeuvre.setEditeur("Gallimard");
        oeuvre.setEtat(EtatOeuvre.nonreservee);

        when(oeuvreService.getOeuvre("1984")).thenReturn(oeuvre);

        // Act & Assert
        mockMvc.perform(get("/voirUneOeuvre/{titre}", "1984"))
                .andExpect(status().isOk())
                .andExpect(view().name("oeuvre"))
                .andExpect(model().attributeExists("oeuvre"))
                .andExpect(model().attribute("oeuvre", hasProperty("titre", is("1984"))))
                .andExpect(model().attribute("oeuvre", hasProperty("auteur", is("George Orwell"))));

        verify(oeuvreService, times(1)).getOeuvre("1984");
    }

    @Test
    @DisplayName("GET /formOeuvre - Devrait afficher le formulaire")
    void createOeuvre_devraitAfficherFormulaire() throws Exception {
        mockMvc.perform(get("/formOeuvre"))
                .andExpect(status().isOk())
                .andExpect(view().name("formNewOeuvre"))
                .andExpect(model().attributeExists("oeuvre"))
                .andExpect(model().attribute("oeuvre", instanceOf(Oeuvre.class)));
    }

    @Test
    @DisplayName("POST /addOeuvre - Devrait sauvegarder et rediriger")
    void saveOeuvre_avecOeuvreValide_devraitSauvegarderEtRediriger() throws Exception {
        // Arrange
        Oeuvre oeuvre = new Oeuvre();
        oeuvre.setTitre("Nouvelle Oeuvre");
        oeuvre.setAuteur("Auteur");
        oeuvre.setEditeur("Editeur");

        when(oeuvreService.saveOeuvre(any(Oeuvre.class))).thenReturn(oeuvre);

        // Act & Assert
        mockMvc.perform(post("/addOeuvre")
                .param("titre", "Nouvelle Oeuvre")
                .param("auteur", "Auteur")
                .param("editeur", "Editeur"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/voirLesOeuvres"));

        verify(oeuvreService, times(1)).saveOeuvre(any(Oeuvre.class));
    }
}
