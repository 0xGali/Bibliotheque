package com.miage.bibliothequeWebapp.service;

import com.miage.bibliothequeWebapp.model.ReservationId;
import com.miage.bibliothequeWebapp.repository.ReservationProxy;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Tests unitaires - ReservationService")
class ReservationServiceTest {

    @Mock
    private ReservationProxy reservationProxy;

    @InjectMocks
    private ReservationService reservationService;

    @Test
    @DisplayName("faireUneReservation - Devrait appeler le proxy")
    void faireUneReservation_avecDonneesValides_devraitAppelerProxy() {
        // Arrange
        ReservationId reservationId = new ReservationId(
            "Le Seigneur des Anneaux",
            "Dupont",
            new Date()
        );

        when(reservationProxy.faireUneReservation(any(ReservationId.class)))
            .thenReturn(reservationId);

        // Act
        ReservationId result = reservationService.faireUneReservation(reservationId);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result.getTitreOeuvre()).isEqualTo("Le Seigneur des Anneaux");
        assertThat(result.getNomUsager()).isEqualTo("Dupont");
        verify(reservationProxy, times(1)).faireUneReservation(reservationId);
    }

    @Test
    @DisplayName("faireUneReservation - Devrait retourner l'ID de réservation")
    void faireUneReservation_devraitRetournerReservationId() {
        // Arrange
        Date date = new Date();
        ReservationId reservationId = new ReservationId("Oeuvre", "Usager", date);
        when(reservationProxy.faireUneReservation(reservationId)).thenReturn(reservationId);

        // Act
        ReservationId result = reservationService.faireUneReservation(reservationId);

        // Assert
        assertThat(result).isEqualTo(reservationId);
        assertThat(result.getDateReservation()).isEqualTo(date);
    }
}
