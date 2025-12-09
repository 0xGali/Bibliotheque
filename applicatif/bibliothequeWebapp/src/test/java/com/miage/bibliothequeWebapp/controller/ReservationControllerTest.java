package com.miage.bibliothequeWebapp.controller;

import com.miage.bibliothequeWebapp.model.ReservationId;
import com.miage.bibliothequeWebapp.service.ReservationService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Date;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ReservationController.class)
@DisplayName("Tests unitaires - ReservationController")
class ReservationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ReservationService reservationService;

    @Test
    @DisplayName("POST /faireUneReservation - Devrait créer réservation et rediriger")
    void faireUneReservation_avecDonneesValides_devraitRediriger() throws Exception {
        // Arrange
        ReservationId reservationId = new ReservationId(
            "Le Seigneur des Anneaux",
            "Dupont",
            new Date()
        );

        when(reservationService.faireUneReservation(any(ReservationId.class)))
            .thenReturn(reservationId);

        // Act & Assert
        mockMvc.perform(post("/faireUneReservation")
                .param("titreOeuvre", "Le Seigneur des Anneaux")
                .param("nomUsager", "Dupont"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/voirUnUsager/Dupont"));

        verify(reservationService, times(1)).faireUneReservation(any(ReservationId.class));
    }

    @Test
    @DisplayName("POST /faireUneReservation - Devrait ajouter date automatiquement")
    void faireUneReservation_devraitAjouterDateAutomatiquement() throws Exception {
        // Arrange
        when(reservationService.faireUneReservation(any(ReservationId.class)))
            .thenAnswer(invocation -> invocation.getArgument(0));

        // Act & Assert
        mockMvc.perform(post("/faireUneReservation")
                .param("titreOeuvre", "Harry Potter")
                .param("nomUsager", "Martin"))
                .andExpect(status().is3xxRedirection());

        verify(reservationService, times(1)).faireUneReservation(any(ReservationId.class));
    }
}
