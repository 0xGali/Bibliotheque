package com.miage.bibliothequeApp.service;

import com.miage.bibliothequeApp.model.*;
import com.miage.bibliothequeApp.repository.OeuvreRepository;
import com.miage.bibliothequeApp.repository.ReservationRepository;
import com.miage.bibliothequeApp.repository.UsagerRepository;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Date;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Tests unitaires - ReservationService")
class ReservationServiceTest {

    @Mock
    private ReservationRepository reservationRepository;

    @Mock
    private OeuvreRepository oeuvreRepository;

    @Mock
    private UsagerRepository usagerRepository;

    @Mock
    private EntityManager entityManager;

    @InjectMocks
    private ReservationService reservationService;

    private ReservationId reservationId;
    private Usager usager;
    private Oeuvre oeuvre;

    @BeforeEach
    void setUp() {
        reservationId = new ReservationId("Le Seigneur des Anneaux", "Dupont", new Date());
        
        usager = new Usager();
        usager.setNom("Dupont");
        usager.setPrenom("Jean");
        
        oeuvre = new Oeuvre();
        oeuvre.setTitre("Le Seigneur des Anneaux");
        oeuvre.setAuteur("Tolkien");
        oeuvre.setEditeur("Gallimard");
        oeuvre.setEtat(EtatOeuvre.nonreservee);
        oeuvre.setNbResa(0);
    }

    @Test
    @DisplayName("Devrait créer une réservation avec succès")
    void faireUneReservation_avecDonneesValides_devraitReussir() {
        // Arrange
        when(reservationRepository.existsById(reservationId)).thenReturn(false);
        when(usagerRepository.findById("Dupont")).thenReturn(Optional.of(usager));
        when(oeuvreRepository.findById("Le Seigneur des Anneaux")).thenReturn(Optional.of(oeuvre));
        when(oeuvreRepository.save(any(Oeuvre.class))).thenReturn(oeuvre);

        // Act
        ReservationId result = reservationService.faireUneReservation(reservationId);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result.getTitreOeuvre()).isEqualTo("Le Seigneur des Anneaux");
        assertThat(result.getNomUsager()).isEqualTo("Dupont");
        
        verify(reservationRepository).saveAndFlush(any(Reservation.class));
        verify(entityManager).clear();
    }

    @Test
    @DisplayName("Devrait incrémenter le compteur de réservations")
    void faireUneReservation_devraitIncrementerNbResa() {
        // Arrange
        when(reservationRepository.existsById(reservationId)).thenReturn(false);
        when(usagerRepository.findById("Dupont")).thenReturn(Optional.of(usager));
        when(oeuvreRepository.findById("Le Seigneur des Anneaux")).thenReturn(Optional.of(oeuvre));
        
        ArgumentCaptor<Oeuvre> oeuvreCaptor = ArgumentCaptor.forClass(Oeuvre.class);

        // Act
        reservationService.faireUneReservation(reservationId);

        // Assert
        verify(oeuvreRepository).save(oeuvreCaptor.capture());
        Oeuvre savedOeuvre = oeuvreCaptor.getValue();
        
        assertThat(savedOeuvre.getNbResa()).isEqualTo(1);
    }

    @Test
    @DisplayName("Devrait changer l'état de l'œuvre en 'reservee'")
    void faireUneReservation_devraitChangerEtatEnReservee() {
        // Arrange
        when(reservationRepository.existsById(reservationId)).thenReturn(false);
        when(usagerRepository.findById("Dupont")).thenReturn(Optional.of(usager));
        when(oeuvreRepository.findById("Le Seigneur des Anneaux")).thenReturn(Optional.of(oeuvre));
        
        ArgumentCaptor<Oeuvre> oeuvreCaptor = ArgumentCaptor.forClass(Oeuvre.class);

        // Act
        reservationService.faireUneReservation(reservationId);

        // Assert
        verify(oeuvreRepository).save(oeuvreCaptor.capture());
        Oeuvre savedOeuvre = oeuvreCaptor.getValue();
        
        assertThat(savedOeuvre.getEtat()).isEqualTo(EtatOeuvre.reservee);
    }

    @Test
    @DisplayName("Devrait lancer exception si réservation existe déjà")
    void faireUneReservation_avecReservationExistante_devraitLancerException() {
        // Arrange
        when(reservationRepository.existsById(reservationId)).thenReturn(true);

        // Act & Assert
        assertThatThrownBy(() -> reservationService.faireUneReservation(reservationId))
            .isInstanceOf(RuntimeException.class)
            .hasMessageContaining("Une réservation existe déjà");
        
        verify(reservationRepository, never()).saveAndFlush(any());
        verify(oeuvreRepository, never()).save(any());
    }

    @Test
    @DisplayName("Devrait lancer exception si usager inexistant")
    void faireUneReservation_avecUsagerInexistant_devraitLancerException() {
        // Arrange
        when(reservationRepository.existsById(reservationId)).thenReturn(false);
        when(usagerRepository.findById("Dupont")).thenReturn(Optional.empty());

        // Act & Assert
        assertThatThrownBy(() -> reservationService.faireUneReservation(reservationId))
            .isInstanceOf(RuntimeException.class)
            .hasMessageContaining("Usager non trouvé");
        
        verify(reservationRepository, never()).saveAndFlush(any());
    }

    @Test
    @DisplayName("Devrait lancer exception si œuvre inexistante")
    void faireUneReservation_avecOeuvreInexistante_devraitLancerException() {
        // Arrange
        when(reservationRepository.existsById(reservationId)).thenReturn(false);
        when(usagerRepository.findById("Dupont")).thenReturn(Optional.of(usager));
        when(oeuvreRepository.findById("Le Seigneur des Anneaux")).thenReturn(Optional.empty());

        // Act & Assert
        assertThatThrownBy(() -> reservationService.faireUneReservation(reservationId))
            .isInstanceOf(RuntimeException.class)
            .hasMessageContaining("Œuvre non trouvée");
        
        verify(reservationRepository, never()).saveAndFlush(any());
    }

    @Test
    @DisplayName("Devrait incrémenter nbResa même si déjà réservée")
    void faireUneReservation_avecOeuvreDejaReservee_devraitIncrementerNbResa() {
        // Arrange
        oeuvre.setEtat(EtatOeuvre.reservee);
        oeuvre.setNbResa(3);
        
        when(reservationRepository.existsById(reservationId)).thenReturn(false);
        when(usagerRepository.findById("Dupont")).thenReturn(Optional.of(usager));
        when(oeuvreRepository.findById("Le Seigneur des Anneaux")).thenReturn(Optional.of(oeuvre));
        
        ArgumentCaptor<Oeuvre> oeuvreCaptor = ArgumentCaptor.forClass(Oeuvre.class);

        // Act
        reservationService.faireUneReservation(reservationId);

        // Assert
        verify(oeuvreRepository).save(oeuvreCaptor.capture());
        assertThat(oeuvreCaptor.getValue().getNbResa()).isEqualTo(4);
    }
}